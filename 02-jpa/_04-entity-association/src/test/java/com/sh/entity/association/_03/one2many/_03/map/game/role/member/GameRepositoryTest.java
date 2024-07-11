package com.sh.entity.association._03.one2many._03.map.game.role.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GameRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    void setUp() {
        this.entityManager = entityManagerFactory.createEntityManager();
        this.entityManager.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @AfterAll
    static void afterAll() {
        entityManagerFactory.close();
    }

    List<Member> members = List.of(
            new Member("honggd", "홍길동"),
            new Member("sinsa", "신사임당"),
            new Member("leess", "이순신")
    );

    @DisplayName("Game - Member 등록")
    @Test
    public void test() throws Exception {
        // given
        this.members.forEach(this.entityManager::persist);
        Map<String, Member> rollMemberMap = Map.of(
                "S", members.get(0),
                "SG", members.get(1),
                "FF", members.get(2));
        Game game = new Game("g1", "반지의 제왕전", rollMemberMap);
        this.entityManager.persist(game);
        /*
            Hibernate:
                insert
                into
                    game_member
                    (name, id)
                values
                    (?, ?)
            Hibernate:
                insert
                into
                    game_member
                    (name, id)
                values
                    (?, ?)
            Hibernate:
                insert
                into
                    game_member
                    (name, id)
                values
                    (?, ?)
            Hibernate:
                insert
                into
                    game
                    (name, id)
                values
                    (?, ?)
            Hibernate:
                update
                    game_member
                set
                    game_id=?,
                    role_name=?
                where
                    id=?
            Hibernate:
                update
                    game_member
                set
                    game_id=?,
                    role_name=?
                where
                    id=?
            Hibernate:
                update
                    game_member
                set
                    game_id=?,
                    role_name=?
                where
                    id=?
         */
        // when & then
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
    public void test2() throws Exception {
        // given
//        Member honggd = memberRepository.findById("honggd").get();
//        Member sinsa = memberRepository.findById("sinsa").get();
//        Member leess = memberRepository.findById("leess").get();
//        Map<String, Member> rollMemberMap = Map.of("S", honggd, "SG", sinsa, "FF", leess);
//        Game game = new Game("g1", "반지의 제왕전", rollMemberMap);
//        game = gameRepository.saveAndFlush(game);
//        System.out.println(game);
//        // when
//        game.removeRole("S");
//        game = gameRepository.saveAndFlush(game);
//        // then
//        assertThat(game.getMembers()).containsOnlyKeys("SG","FF");
    }
}