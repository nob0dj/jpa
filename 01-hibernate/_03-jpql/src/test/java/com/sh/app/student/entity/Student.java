package com.sh.app.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortNatural;

@Entity
@Table(name = "tb_student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Comparable<Student> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SortNatural
    private Long id;
    private String name;
    @Convert(converter = DepartmentConverter.class)
    private Department department;

    @Override
    public int compareTo(Student other) {
        return (int) (this.id - other.id);
    }
}
