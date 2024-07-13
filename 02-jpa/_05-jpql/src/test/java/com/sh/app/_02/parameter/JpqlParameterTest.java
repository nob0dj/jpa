package com.sh.app._02.parameter;


import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <pre>
 * 파라미터 바인딩하는 방법
 * 1. 이름 기준 파라미터(named parameters)
 * 	  ':' 다음에 이름 기준 파라미터를 지정한다. :name
 * 	   
 * 2. 위치 기준 파라미터(positional parameters)
 *    '?' 다음에 값을 주고 위치 값은 1부터 시작한다. ?1
 * </pre>
 */
public class JpqlParameterTest {
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
    @DisplayName("이름 파라미터 테스트")
    void test1() {
        // given
        String menuName = "한우딸기국밥";
        String orderableStatus = "Y";
        String jpql = """
                select
                    m
                from
                    Menu02 m
                where
                    m.orderableStatus = :orderableStatus
                    and m.menuName = :menuName
                """;
        // when
//        TypedQuery<Menu> query = this.entityManager.createQuery(jpql, Menu.class);
//        query.setParameter("menuName", menuName);
        TypedQuery<Menu> query = this.entityManager.createQuery(jpql, Menu.class)
                            .setParameter("menuName", menuName)
                            .setParameter("orderableStatus", orderableStatus);
        List<Menu> menus = query.getResultList();
        /*
            select
                m1_0.menu_code,
                m1_0.category_code,
                m1_0.menu_name,
                m1_0.menu_price,
                m1_0.orderable_status
            from
                tbl_menu m1_0
            where
                m1_0.orderable_status=?
                and m1_0.menu_name=?
         */
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotNull()
                .allMatch((menu) ->
                        menu.getMenuName().equals(menuName)
                        && menu.getOrderableStatus().equals(orderableStatus));
    }
    
    @Test
    @DisplayName("Like 연산처리")
    void test2() {
        // given
        String menuName = "밥";
        String jpql = """
                select
                    m
                from
                    Menu02 m
                where
                    m.menuName like :menuName
                """;
        // when
        TypedQuery<Menu> query = this.entityManager.createQuery(jpql, Menu.class)
                        .setParameter("menuName", "%" + menuName + "%");
        List<Menu> menus = query.getResultList();
        /*
            select
                m1_0.menu_code,
                m1_0.category_code,
                m1_0.menu_name,
                m1_0.menu_price,
                m1_0.orderable_status
            from
                tbl_menu m1_0
            where
                m1_0.menu_name like ? escape ''
         */
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotNull()
                .allMatch((menu) -> menu.getMenuName().contains(menuName));
    }
    
    @Test
    @DisplayName("in 연산자")
    void test3() {
        // given
        List<Long> categoryCodes = List.of(4L, 5L, 6L);
        String jpql = """
                select
                    m
                from
                    Menu02 m
                where
                    m.categoryCode in :categoryCodes
                order by
                    m.menuName asc
                """;
        // when
        TypedQuery<Menu> query = this.entityManager.createQuery(jpql, Menu.class)
                                    .setParameter("categoryCodes", categoryCodes);
        List<Menu> menus = query.getResultList();
        /*
            select
                m1_0.menu_code,
                m1_0.category_code,
                m1_0.menu_name,
                m1_0.menu_price,
                m1_0.orderable_status
            from
                tbl_menu m1_0
            where
                m1_0.category_code in (?, ?, ?)
            order by
                m1_0.menu_name
         */
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotNull()
                .allMatch((menu) -> categoryCodes.contains(menu.getCategoryCode()));
    }

    @Test
    @DisplayName("위치 파라미터 테스트")
    void test4() {
        // given
        String menuName = "한우딸기국밥";
        String orderableStatus = "Y";
        String jpql = """
                select
                    m
                from
                    Menu02 m
                where
                    m.orderableStatus = ?1
                    and m.menuName = ?2
                """;
        // when
        TypedQuery<Menu> query = this.entityManager.createQuery(jpql, Menu.class)
                .setParameter(1, orderableStatus)
                .setParameter(2, menuName);
        List<Menu> menus = query.getResultList();
        /*
            select
                m1_0.menu_code,
                m1_0.category_code,
                m1_0.menu_name,
                m1_0.menu_price,
                m1_0.orderable_status
            from
                tbl_menu m1_0
            where
                m1_0.orderable_status=?
                and m1_0.menu_name=?
         */
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotNull()
                .allMatch((menu) ->
                        menu.getMenuName().equals(menuName)
                                && menu.getOrderableStatus().equals(orderableStatus));
    }
}
