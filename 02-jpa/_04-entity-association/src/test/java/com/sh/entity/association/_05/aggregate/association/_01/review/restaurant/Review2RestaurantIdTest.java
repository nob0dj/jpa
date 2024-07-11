package com.sh.entity.association._05.aggregate.association._01.review.restaurant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class Review2RestaurantIdTest {
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
            create table restaurant_00 (
                id varchar(255) not null,
                name varchar(255),
                primary key (id)
            ) engine=InnoDB

            create table restaurant_review_00 (
                grade integer not null,
                id bigint not null auto_increment,
                restaurant_id varchar(255),
                comment varchar(255),
                primary key (id)
            ) engine=InnoDB
         */
    }

    @DisplayName("Review 등록 및 조회")
    @Test
    public void test2() throws Exception {
        // given
        Restaurant restaurant = Restaurant.builder()
                .id(RestaurantId.generate()) // id 생성
                .name("하누소")
                .build();
        Review review = Review.builder()
                .restaurantId(restaurant.getId())
                .comment("#JMT")
                .grade(5)
                .build();
        // when
        this.entityManager.persist(restaurant);
        this.entityManager.persist(review);
        this.entityManager.flush();

        this.entityManager.clear();
        Review review2 = this.entityManager.find(Review.class, review.getId());
        System.out.println(review2);
        // then
        assertThat(review2).isNotNull();
        assertThat(review2.getRestaurantId()).isNotNull()
                .isEqualTo(restaurant.getId());


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
        Restaurant restaurant = Restaurant.builder()
                .id(RestaurantId.generate()) // id 생성
                .name("서울김밥")
                .build();
        Review review1 = Review.builder()
                .restaurantId(restaurant.getId())
                .comment("#JMT")
                .grade(5)
                .build();
        Review review2 = Review.builder()
                .restaurantId(restaurant.getId())
                .comment("떡뽂이가 이럴수 있다니...")
                .grade(5)
                .build();
        this.entityManager.persist(restaurant);
        this.entityManager.persist(review1);
        this.entityManager.persist(review2);
        this.entityManager.flush();
        this.entityManager.clear();
        // when
        String jpql = """
                select
                    r
                from
                    Review00 r
                where
                    r.restaurantId = :restaurantId
                """;
        TypedQuery<Review> query = this.entityManager.createQuery(jpql, Review.class)
                .setParameter("restaurantId", restaurant.getId());
        List<Review> reviews = query.getResultList();
        reviews.forEach(System.out::println);
        // then
        assertThat(reviews).isNotNull()
                .allMatch((review) -> review.getRestaurantId().equals(restaurant.getId()));
    }

}