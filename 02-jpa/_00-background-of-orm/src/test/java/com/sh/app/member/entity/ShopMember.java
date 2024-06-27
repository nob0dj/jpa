package com.sh.app.member.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopMember extends Member {
    private String address;

    public ShopMember(Long id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}
