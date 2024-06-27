package com.sh.app.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminMember extends Member {
    private String adminRole;

    public AdminMember(Long id, String name, String adminRole) {
        super(id, name);
        this.adminRole = adminRole;
    }
}
