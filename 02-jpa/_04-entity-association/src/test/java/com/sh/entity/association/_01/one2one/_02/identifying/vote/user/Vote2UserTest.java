package com.sh.entity.association._01.one2one._02.identifying.vote.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Vote2UserTest {
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
            vote테이블에서는 참조하는 user_email을 pk컬럼으로 사용한다.

            Hibernate:
                create table user2 (
                    createdAt datetime(6),
                    email varchar(255) not null,
                    name varchar(255),
                    primary key (email)
                ) engine=InnoDB
            Hibernate:
                create table vote (
                    candidate_name varchar(255),
                    user_email varchar(255) not null,
                    primary key (user_email)
                ) engine=InnoDB
         */
    }


    /**
     * <pre>
     * 테스트 환경에서 Vote#user:User에 @OneToOne(cascade = CascadeType.ALL)이 필요하다.
     * 없다면, vote객체 저장시 null identifier (com.sh.entity.association._01.one2one._02.identifying.user.vote.entity.Vote) 오류난다.
     *
     * TODO: 실제 실행환경에서는 다를까?
     *
     * </pre>
     * @throws Exception
     */
    @DisplayName("Vote-User 등록")
    @Test
    public void test2() throws Exception {
        // given
        User user = User.builder()
                .email("honggd@gmail.com")
                .name("홍길동")
                .build();
        Vote vote = new Vote(user, "허경영");
        this.entityManager.persist(user);
        this.entityManager.persist(vote);
        this.entityManager.flush();
        /*
        Hibernate:
            insert
            into
                user2
                (createdAt, name, email)
            values
                (?, ?, ?)
        Hibernate:
            insert
            into
                vote
                (candidate_name, user_email)
            values
                (?, ?)
         */
        // when
        this.entityManager.clear();
        Vote vote2 = this.entityManager.find(Vote.class, user.getEmail());
        /*
        select
            v1_0.user_email,
            v1_0.candidate_name,
            u1_0.email,
            u1_0.createdAt,
            u1_0.name
        from
            vote v1_0
        left join
            user2 u1_0
                on u1_0.email=v1_0.user_email
        where
            v1_0.user_email=?
         */
        System.out.println(vote2);
        // then
        assertThat(vote2).isNotNull();
        assertThat(vote2.getUser()).isNotNull();
    }
    @DisplayName("Vote-User(null) 등록하면 오류가 발생한다.")
    @Test
    public void test3() throws Exception {
        // given
        User user = null;
        // when & then
        assertThatThrownBy(() -> {
            Vote vote = new Vote(user, "허경영");
        }).isInstanceOf(AssertionError.class);
    }
}