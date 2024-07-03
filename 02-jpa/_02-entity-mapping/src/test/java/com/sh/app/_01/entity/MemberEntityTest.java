package com.sh.app._01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberEntityTest {
    // application-scope: 1개만 만들어서 재사용 (thread-safe)
    private static EntityManagerFactory entityManagerFactory;
    // request-scope: 웹요청마다 1개씩 생성 (non-thread-safe)
    private EntityManager entityManager;

    @BeforeAll
    static void beforeAll() {
        // jpa설정정보를 읽어서 EntityManagerFactory를 생성
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
        // 테이블이 생성되었나요?
        // 컬럼순서는 abc순으로 생성
        /*
            drop table if exists tbl_member01
            create table tbl_member01 (
                member_enabled bit,
                member_created_at datetime(6),
                member_id varchar(255) not null,
                member_name varchar(255),
                member_password varchar(255),
                primary key (member_id)
            ) engine=InnoDB
         */
    }
    
    @Test
    @DisplayName("Member Entity객체 등록")
    void test2() {
        // given
        Member member = new Member();
        member.setId("honggd");
        member.setPassword("1234");
        member.setName("홍길동");
        member.setCreatedAt(LocalDateTime.now());
        member.setEnabled(true);

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(member);
            transaction.commit();
            /*
                insert
                into
                    tbl_member01
                    (member_created_at, member_enabled, member_name, member_password, member_id)
                values
                    (?, ?, ?, ?, ?)
             */
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        // then
        Member member2 = entityManager.find(Member.class, member.getId());
        assertThat(member2).isNotNull();
    }

}
