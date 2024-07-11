package com.sh.entity.association._01.one2one._01.non.identifying.card.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

class Card2UserTest {
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
            create table membership_card (
                enabled bit not null,
                expiry_date date,
                card_no varchar(255) not null,
                user_email varchar(255),
                primary key (card_no)
            ) engine=InnoDB
        Hibernate:
            create table user (
                createdAt datetime(6),
                email varchar(255) not null,
                name varchar(255),
                primary key (email)
            ) engine=InnoDB
        Hibernate:
            alter table membership_card
               add constraint UKdddgxye1s2k0n69xsmp7d8nfq unique (user_email)
        Hibernate:
            alter table membership_card
               add constraint FKqndgiu1sewhbwffw3bpfe4owh
               foreign key (user_email)
               references user (email)
         */
    }

    @DisplayName("Card - User 등록")
    @Test
    public void test2() throws Exception {
        // given
        User user = User.builder()
                .email("honggd@gmail.com")
                .name("홍길동")
                .build();
        Card card = Card.builder()
                .cardNumber("1111-2222-3333-4444")
                .owner(user)
                .expiryDate(YearMonth.of(2030, 11))
                .enabled(true)
                .build();
        // when
        this.entityManager.persist(user);
        this.entityManager.persist(card);
        this.entityManager.flush();
        // then
        this.entityManager.clear();
        Card card2 = this.entityManager.find(Card.class, card.getCardNumber());
        /*
            select
                c1_0.card_no,
                c1_0.enabled,
                c1_0.expiry_date,
                o1_0.email,
                o1_0.createdAt,
                o1_0.name
            from
                membership_card c1_0
            left join
                user o1_0
                    on o1_0.email=c1_0.user_email
            where
                c1_0.card_no=?
         */
        System.out.println(card2);
        assertThat(card2).isNotNull();
        assertThat(card2.getOwner()).isNotNull();
    }

    @DisplayName("Card - User(null) 등록")
    @Test
    public void test3() throws Exception {
        // given
        Card card = Card.builder()
                .cardNumber("2222-3333-4444-5555")
                .expiryDate(YearMonth.of(2030, 11))
                .enabled(true)
                .build();
        // when
        this.entityManager.persist(card);
        this.entityManager.flush();
        // then
        this.entityManager.clear();
        Card card2 = this.entityManager.find(Card.class, card.getCardNumber());
        System.out.println(card2);
        assertThat(card2).isNotNull();
        assertThat(card2.getOwner()).isNull();
    }
}