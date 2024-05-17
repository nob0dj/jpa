package com.sh.entity.association._03.one2many._03.map.game.role.member.repository;

import com.sh.entity.association._03.one2many._03.map.game.role.member.entity.Game;
import com.sh.entity.association._03.one2many._03.map.game.role.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        List<Member> members = List.of(
            new Member("honggd", "홍길동"),
            new Member("sinsa", "신사임당"),
            new Member("leess", "이순신")
        );
        memberRepository.saveAllAndFlush(members);
    }

    @DisplayName("Game - Member 등록")
    @Test
    public void test() throws Exception {
        // given
        Member honggd = memberRepository.findById("honggd").get();
        Member sinsa = memberRepository.findById("sinsa").get();
        Member leess = memberRepository.findById("leess").get();
        Map<String, Member> rollMemberMap = Map.of("S", honggd, "SG", sinsa, "FF", leess);
        Game game = new Game("g1", "반지의 제왕전", rollMemberMap);
        // when
        game = gameRepository.saveAndFlush(game);
        System.out.println(game);
        // then
        assertThat(game.getMembers()).containsExactlyEntriesOf(rollMemberMap);
    }

    /**
     * <pre>
     * Map의 entry를 삭제해도 연관테이블의 행은 삭제되지 않는다.
     * - 관련된 컬럼값을 null로 처리하는 update문이 실행된다.
     *
     * <code>
     *     update
     *         game_member
     *     set
     *         game_id=null,
     *         role_name=null
     *     where
     *         game_id=?
     *         and id=?
     * </code>
     * </pre>
     * <img width="500" src="https://d.pr/i/jQjvPI+"/>
     * @throws Exception
     */
    @DisplayName("Game - Member 수정")
    @Test
    @Rollback(false)
    public void test2() throws Exception {
        // given
        Member honggd = memberRepository.findById("honggd").get();
        Member sinsa = memberRepository.findById("sinsa").get();
        Member leess = memberRepository.findById("leess").get();
        Map<String, Member> rollMemberMap = Map.of("S", honggd, "SG", sinsa, "FF", leess);
        Game game = new Game("g1", "반지의 제왕전", rollMemberMap);
        game = gameRepository.saveAndFlush(game);
        System.out.println(game);
        // when
        game.removeRole("S");
        game = gameRepository.saveAndFlush(game);
        // then
        assertThat(game.getMembers()).containsOnlyKeys("SG","FF");
    }
}