package com.sh.entity.association._03.one2many._01.set.team.player.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Team {
    @Id
    private String id;
    private String name;
    @OneToMany
    @JoinColumn(name = "team_id")
    private Set<Player> players = new HashSet<>();

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }

    public void removeAllPlayers() {
        players.clear();
    }
}
