package com.sh.entity.association._03.one2many._03.map.game.role.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "game_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Member {
    @Id
    private String id;
    private String name;
}
