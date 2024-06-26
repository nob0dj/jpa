package com.sh.app.user;

import com.sh.app.user.entity.User;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * repository 거치지 않고, hibernate를 사용해 직접 db에 테스트한다.
 */
public class UserPersitenceTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        session = entityManager.unwrap(Session.class);
    }

    @AfterAll
    public static void afterAll() {
        entityManagerFactory.close();
        sessionFactory.close();
    }

    /**
     * 기본값처리 유의
     * - @DynamicInsert + default값 : 영속entity 적용안됨.
     * - @PrePersist : 영속entity 바로 적용됨.
     * - @CreationTimestamp : 영속entity 바로 적용됨.
     *
     * @throws Exception
     */
    @DisplayName("회원등록 - 메모리")
    @Test
    public void test1() throws Exception {
        // given
        User user = User.builder()
                .username("honggd")
                .build();
        System.out.println(user);
        // when
        entityManager.persist(user); // id 발급
        System.out.println(user);
        // then
        Assertions.assertThat(user.getId()).isNotNull().isNotZero();
    }

    @DisplayName("회원등록 - DB쓰기")
    @Test
    public void test1_2() throws Exception {
        // given
        User user = User.builder()
                .username("honggd")
//                .createdAt(LocalDateTime.now()) // @CreationTimestamp 작성이후 주석
                .build();
        System.out.println(user);

        // when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(user); // id 발급
            entityTransaction.commit(); // commit할때 @CreationTimestamp 작동
            System.out.println(user);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        // then
        Assertions.assertThat(user.getId()).isNotNull().isNotZero();
        Assertions.assertThat(user.getPoint()).isNotNull().isGreaterThanOrEqualTo(1000);
        Assertions.assertThat(user.getCreatedAt()).isNotNull();
    }

    @DisplayName("존재하는 회원 1명 조회")
    @Test
    public void test2() throws Exception {
        // when
        User user = User.builder()
                .username("sinsa")
                .build();
        System.out.println(user);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(user); // id 발급
//            entityTransaction.commit(); // commit할때 @CreationTimestamp 작동
            System.out.println(user);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        // then
        Assertions.assertThat(user.getId()).isNotNull().isNotZero();
        User user2 = entityManager.find(User.class, user.getId());
        Assertions.assertThat(user2)
                .isNotNull()
                .satisfies((_user) -> {
                    Assertions.assertThat(_user.getId()).isEqualTo(user.getId());
                    Assertions.assertThat(_user.getUsername()).isEqualTo(user.getUsername());
                    Assertions.assertThat(user.getPoint()).isNotNull().isGreaterThanOrEqualTo(1000);
                    Assertions.assertThat(_user.getCreatedAt()).isEqualTo(user.getCreatedAt());
                });
    }

    @DisplayName("존재하지 않는 회원 1명 조회")
    @Test
    public void test2_2() throws Exception {
        // given
        Long id = 99999999999999L;
        // when
        User user = entityManager.find(User.class, id);
        // then
        Assertions.assertThat(user).isNull();
    }


    @DisplayName("전체조회 w/ jpql(Query)")
    @Test
    public void test3() throws Exception {
        // given
        // when
        Query query = session.createQuery("SELECT u FROM user u", User.class);
        List<User> users = query.getResultList();
        // then
        System.out.println(users);
        Assertions.assertThat(users)
                .isNotNull()
                .allSatisfy((user -> {
                    Assertions.assertThat(user.getId()).isNotNull().isNotZero();
                    Assertions.assertThat(user.getUsername()).isNotNull();
                    Assertions.assertThat(user.getPoint()).isNotNull().isGreaterThanOrEqualTo(1000);
                    Assertions.assertThat(user.getCreatedAt()).isNotNull();
                }));
    }


    @DisplayName("전체조회 w/ Criteria API (TypedQuery)")
    @Test
    public void test3_2() throws Exception {
        // given
        // when
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        TypedQuery<User> typedQuery = session.createQuery(all);
        List<User> users = typedQuery.getResultList();
        // then
        System.out.println(users);
        Assertions.assertThat(users)
                .isNotNull()
                .allSatisfy((user -> {
                    Assertions.assertThat(user.getId()).isNotNull().isNotZero();
                    Assertions.assertThat(user.getUsername()).isNotNull();
                    Assertions.assertThat(user.getCreatedAt()).isNotNull();
                }));
    }
}
