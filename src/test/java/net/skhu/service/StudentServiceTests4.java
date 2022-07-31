package net.skhu.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.skhu.dto.Student;
import net.skhu.mapper.StudentMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTests4 {

    @Mock StudentMapper studentMapper;
    @InjectMocks StudentService studentService;

    Student student;

    @Before
    public void prepare() {
        // 테스트 메소드들에서 사용할 객체를 미리 생성함
        this.student = new Student();
        this.student.setId(337);
        this.student.setStudentNo("201132011");
        this.student.setName("임꺽정");
        this.student.setDepartmentId(2);
        this.student.setPhone("010-123-4567");
        this.student.setEmail("lim@skhu.ac.kr");
    }

    @Test
    public void test_insert_OK() {
        Mockito.when(studentMapper.findByStudentNo(ArgumentMatchers.anyString()))
               .thenReturn(null);
        studentService.insert(this.student);  // 저장
        Mockito.verify(studentMapper).insert(this.student); // insert 호출되었는지 확인
    }

    @Test
    public void test_insert_학번중복() {
        Mockito.when(studentMapper.findByStudentNo(ArgumentMatchers.anyString()))
               .thenReturn(this.student);
        studentService.insert(this.student);  // 저장
        // 호출 안됨 확인
        Mockito.verify(studentMapper, Mockito.times(0)).insert(ArgumentMatchers.any());
    }
}

