package com.sh.app._01.jpql;


import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * <h1>JPQL(Java Persistence Query Language)</h1>
 * <pre>
 * - JPQL은 엔터티 객체를 중심으로 개발할 수 있는 객체 지향 쿼리이다.
 * - SQL보다 간결하며 특정 DBMS에 의존하지 않는다.
 * - 방언을 통해 해당 DBMS에 맞는 SQL을 실행하게 된다.
 * - JPQL은 find() 메소드를 통한 조회와 다르게 항상 데이터베이스에 SQL을 실행해서 결과를 조회한다.
 * </pre>
 *
 * <h2>JPQL의 기본 문법</h2>
 * <pre>
 * SELECT, UPDATE, DELETE 등의 키워드 사용은 SQL과 동일하다.
 * INSERT 는 persist() 메소드를 사용하면 된다.
 * 키워드(SELECT, FROM, WHERE, ...)는 대소문자를 구분하지 않지만, 엔터티와 속성은 대소문자를 구분함에 유의한다.
 * 엔터티 별칭을 필수로 사용해야 하며 별칭 없이 작성하면 에러가 발생한다.
 *
 *
 * JPQL 사용 방법은 다음과 같다.
 * 1. 작성한 JPQL(문자열)을 `entityManager.createQuery()` 메소드를 통해 쿼리 객체로 만든다.
 * 2. 쿼리 객체는 `TypedQuery`, `Query` 두 가지가 있다.
 * 	- TypedQuery : 반환할 타입을 명확하게 지정하는 방식일 때 사용하며 쿼리 객체의 메소드 실행 결과로 지정한 타입이 반환 된다.
 * 	- Query : 반환할 타입을 명확하게 지정할 수 없을 때 사용하며 쿼리 객체 메소드의 실행 결과로 Object 또는 Object[]이 반환 된다.
 * 3. 쿼리 객체에서 제공하는 메소드 `getSingleResult()` 또는 `getResultList()`를 호출해서 쿼리를 실행하고 데이터베이스를 조회한다.
 * 	- getSingleResult() : 결과가 정확히 한 행일경우 사용하며 없거나 많으면 예외가 발생한다.
 * 	- getResultList() : 결과가 2행 이상일 경우 사용하며 컬렉션을 반환한다. 결과가 없으면 빈 컬렉션을 반환한다.
 * 	</pre>
 */
public class JpqlTest {
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
    @DisplayName("TypedQuery - Menu 테스트")
    void test1() {
        // given
        // 테이블 별칭을 선언하고, select절에서 사용해야 한다. *를 사용할 수 없다.
        String jpql = "select m from Menu01 as m";
        // when
        TypedQuery<Menu> query = this.entityManager.createQuery(jpql, Menu.class);
        List<Menu> menus = query.getResultList();
        /*
            Hibernate:
                select
                    m1_0.menu_code,
                    m1_0.category_code,
                    m1_0.menu_name,
                    m1_0.menu_price,
                    m1_0.orderable_status
                from
                    tbl_menu m1_0
         */
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotEmpty()
                .allMatch((menu) -> menu != null);
    }
    
    @Test
    @DisplayName("TypedQuery - String 테스트")
    void test2() {
        // given
        // select절, where조건절에서도 table이 아닌 entity기준으로 조회한다.
        String jpql = "select m.menuName from Menu01 m where m.menuCode = 7";
        // when
        TypedQuery<String> query = this.entityManager.createQuery(jpql, String.class);
        String menuName = query.getSingleResult();
        /*
            Hibernate:
                select
                    m1_0.menu_name
                from
                    tbl_menu m1_0
                where
                    m1_0.menu_code=7
         */
        System.out.println(menuName);
        // then
        assertThat(menuName).isNotNull();
    }

    /**
     * TypedQuery와는 다르게 반환타입을 컴파일타임에 체크할 수 없다.
     */
    @Test
    @DisplayName("Query - Menu 테스트")
    void test3() {
        // given
        // 테이블 별칭을 선언하고, select절에서 사용해야 한다. *를 사용할 수 없다.
        String jpql = "select m from Menu01 as m";
        // when
        Query query = this.entityManager.createQuery(jpql);
        List<Menu> menus = query.getResultList();
        /*
            Hibernate:
                select
                    m1_0.menu_code,
                    m1_0.category_code,
                    m1_0.menu_name,
                    m1_0.menu_price,
                    m1_0.orderable_status
                from
                    tbl_menu m1_0
         */
        menus.forEach(System.out::println);
        // then
        assertThat(menus).isNotEmpty()
                .allMatch((menu) -> menu != null);
    }
    
    @Test
    @DisplayName("distinct 키워드")
    void test4() {
        // given
        String jpql = """
            select distinct
                m.categoryCode
            from
                Menu01 m
            """;
        // when
        TypedQuery<Long> query = this.entityManager.createQuery(jpql, Long.class);
        List<Long> categoryCodes = query.getResultList();
        categoryCodes.forEach(System.out::println);
        // then
        Set<Long> temp = new HashSet<>(categoryCodes);
        assertThat(categoryCodes.size()).isEqualTo(temp.size());
    }

}
