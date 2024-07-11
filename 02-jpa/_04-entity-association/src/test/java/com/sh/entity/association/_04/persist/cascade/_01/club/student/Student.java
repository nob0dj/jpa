package com.sh.entity.association._04.persist.cascade._01.club.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "student")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Student {
    @Id
    private String id;
    private String name;
}
