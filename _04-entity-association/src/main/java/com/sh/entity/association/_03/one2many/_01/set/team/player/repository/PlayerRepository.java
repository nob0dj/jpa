package com.sh.entity.association._03.one2many._01.set.team.player.repository;

import com.sh.entity.association._03.one2many._01.set.team.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
