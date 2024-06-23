package com.sh.entity.mapping._04.inheritance._01.single.table._01.employee.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
//@DiscriminatorValue("developer") // 기본값 Developer
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
public class Developer extends Employee {
    private String lang;

    public Developer(Long id, String name, String contact, String lang) {
        super(id, name, contact);
        this.lang = lang;
    }
}
