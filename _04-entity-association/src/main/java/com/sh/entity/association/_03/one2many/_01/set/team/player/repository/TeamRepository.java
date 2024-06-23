package com.sh.entity.association._03.one2many._01.set.team.player.repository;

import com.sh.entity.association._03.one2many._01.set.team.player.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, String> {
}
