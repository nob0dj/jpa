package com.sh.entity.association._04.persist.cascade._01.club.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "club")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Club {
    @Id
    private String id;
    private String name;
    @OneToMany(cascade = CascadeType.MERGE) // 이때는 PERSIST가 아닌 MERGE를 사용해야 한다?
    @JoinColumn(name = "club_id")
    private Set<Student> students = new HashSet<>();

    public void addStudent(Student p) {
        students.add(p);
    }

    public void removeStudent(Student p) {
        students.remove(p);
    }

    public void removeAllStudents() {
        students.clear();
    }
}
