package net.skhu.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Student {
    int id;
    String studentNo;
    String name;
    int departmentId;
    String phone;
    String email;
    String sex;

    @EqualsAndHashCode.Exclude
    String departmentName;
}

