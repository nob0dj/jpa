package com.sh.app._04._enum;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberEntityEnumTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    void setUp() {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        this.entityManager.close();
    }

    @AfterAll
    static void afterAll() {
        entityManagerFactory.close();
    }

    @Test
    @DisplayName("ddl-auto = create 설정")
    void test1() {
        /*
            create table tbl_member04 (
                member_enabled bit,
                member_gender tinyint check (member_gender between 0 and 1),
                member_code bigint not null auto_increment,
                member_created_at datetime(6),
                member_password varchar(20) not null,
                member_email varchar(255),
                member_id varchar(255),
                member_name varchar(100) default '홍길동',
                member_role enum ('ROLE_ADMIN','ROLE_MEMBER'),
                primary key (member_code)
            ) engine=InnoDB
         */
    }
    
    @Test
    @DisplayName("Member Entity객체 등록")
    void test2() {
        // given
        Member honggd = Member.builder()
                            .id("honggd")
                            .password("1234")
                            .name("홍길동")
                            .gender(Gender.MALE)
                            .memberRole(MemberRole.ROLE_MEMBER)
                            .email("honggd@gmail.com")
                            .createdAt(LocalDateTime.now())
                            .enabled(true)
                            .build();
        Member sinsa = Member.builder()
                            .id("sinsa")
                            .password("1234")
                            .name("신사임당")
                            .gender(Gender.FEMALE)
                            .memberRole(MemberRole.ROLE_ADMIN)
                            .email("sinsa@gmail.com")
                            .createdAt(LocalDateTime.now())
                            .enabled(true)
                            .build();

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(honggd);
            entityManager.persist(sinsa);
            transaction.commit();
            /*
                insert
                    into
                        tbl_member04
                        (member_created_at, member_email, member_enabled, member_gender, member_id, member_role, member_name, member_password)
                    values
                        (?, ?, ?, ?, ?, ?, ?, ?)
                Hibernate:
                    insert
                    into
                        tbl_member04
                        (member_created_at, member_email, member_enabled, member_gender, member_id, member_role, member_name, member_password)
                    values
                        (?, ?, ?, ?, ?, ?, ?, ?)
             */
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println(honggd);
        System.out.println(sinsa);
        // then
        assertThat(honggd.getCode()).isNotNull().isNotZero();
        assertThat(sinsa.getCode()).isNotNull().isNotZero();
    }

}
