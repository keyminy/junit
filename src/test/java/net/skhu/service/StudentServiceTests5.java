package net.skhu.service;

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
public class StudentServiceTests5 {

    @Mock StudentMapper studentMapper;
    @InjectMocks StudentService studentService;

    @Mock Student student;

    @Test
    public void test_insert_중복() {
        Mockito.when(student.getStudentNo()).thenReturn("201132011");
        Mockito.when(studentMapper.findByStudentNo("201132011")).thenReturn(student);

        studentService.insert(this.student);  // 저장

        Mockito.verify(student).getStudentNo();
        Mockito.verify(studentMapper).findByStudentNo("201132011");
        Mockito.verify(studentMapper, Mockito.times(0)).insert(ArgumentMatchers.any());
    }
}

