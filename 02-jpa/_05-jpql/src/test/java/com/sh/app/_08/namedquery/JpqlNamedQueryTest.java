package com.sh.app._08.namedquery;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <pre>
 * Entity클래스에 미리 정의된 jpql을 가져와 질의할 수 있다.
 * </pre>
 */
public class JpqlNamedQueryTest {
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
    @DisplayName("@NamedQuery - findAll")
    void test1() {
        // given
        // when
        TypedQuery<Menu> query = this.entityManager.createNamedQuery("_08.namedquery.Menu.findAll", Menu.class);
        List<Menu> menus = query.getResultList();
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotNull();
    }

    @Test
    @DisplayName("@NamedQuery - findByMenuName")
    void test2() {
        // given
        String menuName = "한우딸기국밥";
        // when
        TypedQuery<Menu> query = this.entityManager.createNamedQuery("_08.namedquery.Menu.findByMenuName", Menu.class)
                                    .setParameter("menuName", menuName);
        List<Menu> menus = query.getResultList();
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotNull()
                .allMatch((menu) -> menu.getMenuName().equals(menuName));
    }

    @Test
    @DisplayName("@NamedQuery - findByMenuNameLike")
    void test3() {
        // given
        String menuName = "밥";
        // when
        TypedQuery<Menu> query = this.entityManager.createNamedQuery("_08.namedquery.Menu.findByMenuNameLike", Menu.class)
                .setParameter("menuName", menuName);
        List<Menu> menus = query.getResultList();
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotNull()
                .allMatch((menu) -> menu.getMenuName().contains(menuName));
    }
}

