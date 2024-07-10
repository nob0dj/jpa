package com.sh.app._03.primary.key._03.uuid.strategy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class GoodsEntityPrimaryKeyUUIDStrategyTest {
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
    @DisplayName("ddl-auto = create 설정")
    void test1() {
        /*
            create table tbl_goods (
                    id binary(16) not null,
                    name varchar(255),
                    primary key (id)
                ) engine=InnoDB
         */
    }
    
    @Test
    @DisplayName("Member Entity객체 등록")
    void test2() {
        // given
        Goods goods = new Goods(null, "젠틀 몬스터 퍼펙트 아이 프로텍터");
        // when
        this.entityManager.persist(goods);
        System.out.println(goods); // Goods(id=401d2ed0-4121-4457-9274-6f5d9875c43f, name=젠틀 몬스터 퍼펙트 아이 프로텍터)
        /*
            Hibernate:
                insert
                into
                    tbl_goods
                    (name, id)
                values
                    (?, ?)
         */
        // then
        assertThat(goods.getId()).isNotNull();
    }

}
