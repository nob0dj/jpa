package com.sh.app._03.primary.key._02.table.strategy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberEntityPrimaryKeyTableStrategyTest {
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
            create table tbl_member0302 (
                member_enabled bit,
                member_code bigint not null,
                member_created_at datetime(6),
                member_password varchar(20) not null,
                member_email varchar(255),
                member_id varchar(255),
                member_name varchar(100) default '홍길동',
                primary key (member_code)
            ) engine=InnoDB

            create table tbl_sequence (
                next_val bigint,
                table_name varchar(255) not null,
                primary key (table_name)
            ) engine=InnoDB

            insert into tbl_sequence(table_name, next_val) values ('tbl_member0302',0)
         */
    }
    
    @Test
    @DisplayName("Member Entity객체 등록")
    void test2() {
        // given
        Member honggd = Member.builder()
                            .id("honggd")
                            .password("1234")
                            .email("honggd@gmail.com")
                            .createdAt(LocalDateTime.now())
                            .enabled(true)
                            .build();
        Member sinsa = Member.builder()
                .id("sinsa")
                .password("1234")
                .email("sinsa@gmail.com")
                .createdAt(LocalDateTime.now())
                .enabled(true)
                .build();

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(honggd);
//            entityManager.persist(sinsa);
            transaction.commit();
            /*
                select
                    tbl.next_val
                from
                    tbl_sequence tbl
                where
                    tbl.table_name=? for update

                update
                    tbl_sequence
                set
                    next_val=?
                where
                    next_val=?
                    and table_name=?

                select
                    tbl.next_val
                from
                    tbl_sequence tbl
                where
                    tbl.table_name=? for update

                update
                    tbl_sequence
                set
                    next_val=?
                where
                    next_val=?
                    and table_name=?

                insert
                into
                    tbl_member0302
                    (member_created_at, member_email, member_enabled, member_id, member_name, member_password, member_code)
                values
                    (?, ?, ?, ?, ?, ?, ?)

                insert
                into
                    tbl_member0302
                    (member_created_at, member_email, member_enabled, member_id, member_name, member_password, member_code)
                values
                    (?, ?, ?, ?, ?, ?, ?)
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
