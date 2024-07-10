package com.sh.app._03.primary.key._04.custom.id.generator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CommodityEntityPrimaryKeyTest {
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
            Hibernate:
                create table tbl_commodity (
                    id varchar(255) not null,
                    name varchar(255),
                    primary key (id)
                ) engine=InnoDB
         */
    }
    
    @Test
    @DisplayName("Commodity Entity객체 등록")
    void test2() {
        // given
        Commodity commodity = new Commodity(null, "해피바스");
        // when
        this.entityManager.persist(commodity);
        System.out.println(commodity); //
        /*
            Hibernate:
                select
                    c1_0.id
                from
                    tbl_commodity c1_0
            Commodity(id=sh-1, name=해피바스)
            Hibernate:
                insert
                into
                    tbl_commodity
                    (name, id)
                values
                    (?, ?)
         */
        // then
        assertThat(commodity.getId()).isNotNull();
    }

}
