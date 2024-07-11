package com.sh.entity.association._03.one2many._03.map.game.role.member;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "game")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Game {
    @Id
    private String id;
    private String name;
    @OneToMany
    @JoinColumn(name = "game_id")
    @MapKeyColumn(name = "role_name")
    private Map<String, Member> members = new HashMap<>();

    public void putRoleMemeber(String role, Member member) {
        members.put(role, member);
    }

    public void removeRole(String role) {
        members.remove(role);
    }
}
