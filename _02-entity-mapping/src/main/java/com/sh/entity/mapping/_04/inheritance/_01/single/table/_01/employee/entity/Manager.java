package com.sh.entity.mapping._04.inheritance._01.single.table._01.employee.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
//@DiscriminatorValue("manager") // 기본값 Manager
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
public class Manager extends Employee {
    private String level;

    public Manager(Long id, String name, String contact, String level) {
        super(id, name, contact);
        this.level = level;
    }
}
