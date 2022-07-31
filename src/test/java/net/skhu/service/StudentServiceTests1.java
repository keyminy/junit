package net.skhu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import net.skhu.dto.Student;
import net.skhu.mapper.StudentMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StudentServiceTests1 {

    @Autowired StudentService studentService;
    @Autowired StudentMapper studentMapper;

    // student 테이블에서 중복되지 않는 새 학번(studentNumber)을 생성하여 리턴한다
    String newStudentNo() {
        for (int i = 201032000; i < 999999999; ++i) {
            String s = String.valueOf(i);
            if (studentMapper.findByStudentNo(s) == null)
                return s;
        }
        return null;
    }

    @Test
    public void test_findAll_findById() {
        // 모든 레코드 조회
        List<Student> list = studentService.findAll();

        for (Student student1 : list) {
            // findById로 레코드 다시 조회
            Student student2 = studentService.findById(student1.getId());

            // 동일한 레코드이므로 값도 동일해야 함
            assertEquals(student1, student2);
        }
    }

    @Test
    public void test_insert_delete() {
        // 새 레코드 객체 생성
        Student student1 = new Student();
        student1.setStudentNo(newStudentNo());
        student1.setName("임꺽정");
        student1.setDepartmentId(2);
        student1.setPhone("010-123-4567");
        student1.setEmail("lim@skhu.ac.kr");
        student1.setSex("남");

        // 저장
        studentService.insert(student1);

        // 잘 저장되었는지 확인
        Student student2 = studentService.findById(student1.getId());
        assertEquals(student1, student2);

        // 삭제
        studentService.deleteById(student1.getId());

        // 삭제 확인
        student2 = studentService.findById(student1.getId());
        assertEquals(student2, null);
    }

    @Test
    public void test_hasErrors_학번중복() {
        Student student = studentService.findAll().get(0);

        BindingResult bindingResult = new BeanPropertyBindingResult(student, "student");
        assertTrue(studentService.hasErrors(student, bindingResult));
    }

    @Test
    public void test_hasErrors_OK() {
        Student student = new Student();
        student.setStudentNo(newStudentNo());

        BindingResult bindingResult = new BeanPropertyBindingResult(student, "student");
        assertFalse(studentService.hasErrors(student, bindingResult));
    }

}

