package com.sh.entity.association._03.one2many._01.set.team.player;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Team {
    @Id
    private String id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id") // player 테이블의 fk 조인컬럼 team_id
    private Set<Player> players = new HashSet<>();

    public Team(String id, String name, Set<Player> players) {
        this.id = id;
        this.name = name;
        this.players = new HashSet<>(players);
    }

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
