package com.sh.entity.association._03.one2many._01.set.team.player.repository;

import com.sh.entity.association._03.one2many._01.set.team.player.entity.Player;
import com.sh.entity.association._03.one2many._01.set.team.player.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @DisplayName("Team - Set<Player> 등록")
    @Test
    public void test() throws Exception {
        // given
        Player player1 = new Player("honggd", "홍길동");
        Player player2 = new Player("sinsa", "신사임당");
        playerRepository.save(player1);
        playerRepository.save(player2);
        Team team = new Team("t-oops", "웁스", Set.of(player1, player2));
        // when
        Team team2 = teamRepository.saveAndFlush(team);
        // then
        assertThat(team2.getPlayers()).isNotNull().isNotEmpty();
    }

    /**
     * <pre>
     * - Team#addPlayer
     * - Team#removePlayer
     * - Team#removeAllPlayers
     * </pre>
     * @throws Exception
     */
    @DisplayName("Team내 Player 변경")
    @Test
    public void test2() throws Exception {

    }
}