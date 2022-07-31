package net.skhu.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.skhu.dto.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StudentMapperTests {

    @Autowired
    StudentMapper studentMapper;

    // student 테이블에서 중복되지 않는 새 학번(studentNo)을 생성하여 리턴한다
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
        List<Student> list = studentMapper.findAll();

        for (Student student1 : list) {
            // findById로 레코드 다시 조회
            Student student2 = studentMapper.findById(student1.getId());

            // 동일한 레코드이므로 값도 동일해야 함
            assertEquals(student1, student2);
        }
    }

    @Test
    public void test_findByStudentNo() {
        // 모든 레코드 조회
        List<Student> list = studentMapper.findAll();
        Student student1 = list.get(0);

        // findByStudentNo로 레코드 다시 조회
        Student student2 = studentMapper.findByStudentNo(student1.getStudentNo());

        // 동일한 레코드이므로 값도 동일해야 함
        assertEquals(student1, student2);
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
        studentMapper.insert(student1);

        // 잘 저장되었는지 확인
        Student student2 = studentMapper.findById(student1.getId());
        assertEquals(student1, student2);

        // 삭제
        studentMapper.deleteById(student1.getId());

        // 삭제 확인
        student2 = studentMapper.findById(student1.getId());
        assertEquals(student2, null);
    }

    @Test
    public void test_update() {
        // 첫번째 레코드
        Student student1 = studentMapper.findAll().get(0);

        // 첫번째 레코드의 모든 멤버 변수 값을 수정한다.
        // 단 id는 제외
        student1.setStudentNo(newStudentNo());
        student1.setName("임꺽정");
        student1.setDepartmentId(2);
        student1.setPhone("010-123-4567");
        student1.setEmail("lim@skhu.ac.kr");
        student1.setSex("남");

        // 저장
        studentMapper.update(student1);

        // 잘 저장되었는지 확인
        Student student2 = studentMapper.findById(student1.getId());
        assertEquals(student1, student2);

        // 다시 값 수정
        student1.setStudentNo(newStudentNo());
        student1.setName("콩쥐");
        student1.setDepartmentId(3);
        student1.setPhone("010-987-6543");
        student1.setEmail("long@skhu.ac.kr");
        student1.setSex("여");

        // 저장
        studentMapper.update(student1);

        // 잘 저장되었는지 확인
        student2 = studentMapper.findById(student1.getId());
        assertEquals(student1, student2);
    }
}
