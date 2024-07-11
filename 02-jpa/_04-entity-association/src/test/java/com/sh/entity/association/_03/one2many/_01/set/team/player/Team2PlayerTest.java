package com.sh.entity.association._03.one2many._01.set.team.player;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Team2PlayerTest {
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

    @Test
    @DisplayName("DDL 확인")
    void test() {
        /*
            Hibernate:
                create table player (
                    id varchar(255) not null,
                    name varchar(255),
                    team_id varchar(255),
                    primary key (id)
                ) engine=InnoDB
            Hibernate:
                create table team (
                    id varchar(255) not null,
                    name varchar(255),
                    primary key (id)
                ) engine=InnoDB
            Hibernate:
                alter table player
                   add constraint FKdvd6ljes11r44igawmpm1mc5s
                   foreign key (team_id)
                   references team (id)
         */
    }

    @DisplayName("Team - Set<Player> 등록")
    @Test
    public void test2() throws Exception {
        // given
        Player player1 = new Player("honggd", "홍길동");
        Player player2 = new Player("sinsa", "신사임당");
        Team team = new Team("oops", "웁스", Set.of(player1, player2));
        this.entityManager.persist(player1);
        this.entityManager.persist(player2);
        this.entityManager.persist(team);
        this.entityManager.flush();
        /*
            Hibernate:
                insert
                into
                    player
                    (name, id)
                values
                    (?, ?)
            Hibernate:
                insert
                into
                    player
                    (name, id)
                values
                    (?, ?)
            Hibernate:
                insert
                into
                    team
                    (name, id)
                values
                    (?, ?)
            Hibernate:
                update
                    player
                set
                    team_id=?
                where
                    id=?
            Hibernate:
                update
                    player
                set
                    team_id=?
                where
                    id=?
         */

        // when
        this.entityManager.clear();
        Team team2 = this.entityManager.find(Team.class, team.getId());
        System.out.println(team2);
        /*
            FetchType.LAZY인 경우 :
            Hibernate:
                select
                    t1_0.id,
                    t1_0.name
                from
                    team t1_0
                where
                    t1_0.id=?
            Hibernate:
                select
                    p1_0.team_id,
                    p1_0.id,
                    p1_0.name
                from
                    player p1_0
                where
                    p1_0.team_id=?

            FetchType.EAGER인 경우 :
                select
                    t1_0.id,
                    t1_0.name,
                    p1_0.team_id,
                    p1_0.id,
                    p1_0.name
                from
                    team t1_0
                left join
                    player p1_0
                        on t1_0.id=p1_0.team_id
                where
                    t1_0.id=?
         */
        // then
        assertThat(team2.getPlayers()).containsExactly(player1, player2);
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
    public void test3() throws Exception {
        // given
        Player player1 = new Player("leess", "리순신");
        Player player2 = new Player("yoogs", "유관순");
        Team team = new Team("OMG", "오마이걸", Set.of(player1, player2));
        this.entityManager.persist(player1);
        this.entityManager.persist(player2);
        this.entityManager.persist(team);
        this.entityManager.flush();
        // when
        Player player3 = new Player("gogd", "고길동");
        team.addPlayer(player3);
        this.entityManager.flush();
        System.out.println(team);
        // then
        // 순서 상관없이 요소 포함여부만 검증
        assertThat(team.getPlayers()).contains(player1, player2, player3);


    }
}