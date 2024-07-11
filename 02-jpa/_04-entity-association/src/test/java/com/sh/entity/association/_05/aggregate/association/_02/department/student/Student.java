package com.sh.entity.association._05.aggregate.association._02.department.student;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_student")
@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "department_id")
    private Long departmentId;
    @Embedded
    private Address address;
}
