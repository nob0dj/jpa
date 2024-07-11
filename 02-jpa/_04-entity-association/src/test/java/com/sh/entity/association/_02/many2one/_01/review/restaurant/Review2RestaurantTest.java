package com.sh.entity.association._02.many2one._01.review.restaurant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class Review2RestaurantTest {
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
            create table restaurant (
                id bigint not null auto_increment,
                name varchar(255),
                primary key (id)
            ) engine=InnoDB

            create table restaurant_review (
                grade integer not null,
                id bigint not null auto_increment,
                restaurant_id bigint,
                comment varchar(255),
                primary key (id)
            ) engine=InnoDB

         */
    }

    @DisplayName("Review 등록 및 조회")
    @Test
    public void test2() throws Exception {
        // given
        Restaurant restaurant = new Restaurant(null, "덮자/덮어/덮은거야");
        Review review = new Review(null, restaurant, 5, "제 최애덮밥집입니다.");
        // when
        this.entityManager.persist(restaurant);
        this.entityManager.persist(review);
        this.entityManager.flush();
        /*
            Hibernate:
                insert
                into
                    restaurant
                    (name)
                values
                    (?)
            Hibernate:
                insert
                into
                    restaurant_review
                    (comment, grade, restaurant_id)
                values
                    (?, ?, ?)
         */
        // then
        assertThat(review.getId()).isNotNull().isNotZero();

        this.entityManager.clear();
        Review review2 = this.entityManager.find(Review.class, review.getId());
        /*
            select
                r1_0.id,
                r1_0.comment,
                r1_0.grade,
                r2_0.id,
                r2_0.name
            from
                restaurant_review r1_0
            left join
                restaurant r2_0
                    on r2_0.id=r1_0.restaurant_id
            where
                r1_0.id=?
         */
        System.out.println(review2);
        assertThat(review2.getRestaurant()).isNotNull();
    }

    /**
     * <pre>
     * N:1(Review -> Restaurant) 반대의 경우 Restaurant -> Review 조회하기
     * - select * from review where restaurant_id = ?
     * jpql 또는 query method로 해결할 수 있다.
     * </pre>
     * @throws Exception
     */
    @DisplayName("특정 Restaurant의 Review 조회하기 - jpql 또는 query method")
    @Test
    public void test3() throws Exception {
        // given
        // when
        String jpql = "select r from Review r where r.restaurant = :restaurant";
        TypedQuery<Review> query = this.entityManager.createQuery(jpql, Review.class)
                .setParameter("restaurant", Restaurant.builder().id(1L).build());
        List<Review> reviews = query.getResultList();
        System.out.println(reviews);
        // then
        assertThat(reviews).isNotEmpty();
    }

}