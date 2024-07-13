package com.sh.app._07.subquery;


import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <pre>
 * SQL처럼 서브쿼리를 지원하지만, 
 * select절, from절에 사용할 수 없고,
 * where절, having절에서만 사용할 수 있다.
 * </pre>
 */
public class JpqlSubqueryTest {
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
    @DisplayName("서브쿼리 테스트")
    void test1() {
        // given
        String categoryName = "한식";
        // when
        String jpql = """
                select
                    m
                from
                    Menu07 m
                where
                    m.categoryCode = (
                        select 
                            c.categoryCode 
                        from 
                            Category07 c 
                        where 
                            c.categoryName = :categoryName)
                """;
        TypedQuery<Menu> query = this.entityManager.createQuery(jpql, Menu.class)
                                    .setParameter("categoryName", categoryName);
        List<Menu> menus = query.getResultList();
        menus.forEach(System.out::println);

        // then
        Long categoryCode = 4L; // "한식"의 카테고리번호
        assertThat(menus).isNotNull()
                .allMatch((menu) -> menu.getCategoryCode() == categoryCode);
    }
}

