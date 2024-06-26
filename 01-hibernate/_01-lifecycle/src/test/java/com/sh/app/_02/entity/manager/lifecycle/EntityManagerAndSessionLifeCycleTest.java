package com.sh.app._02.entity.manager.lifecycle;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

/**
 * <pre>
 * java진영 orm규격 jpa소속 인터페이스. 모든 java orm구현체는 이 규격을 준수해야 한다.
 * - jakarta.persistence.EntityManager;
 * - jakarta.persistence.EntityManagerFactory;
 *
 * 구현체인 hibernate 소속 인터페이스. eclipselink등 다른 orm을 사용하는 경우 달라질 수 있다.
 * - org.hibernate.Session;
 * - org.hibernate.SessionFactory;
 *
 * <strong>아래는 EntityMangerFactory를 통해 SessionFactory를, EntityManger를 통해 Session객체를 가져오기를 테스트한다.</strong>
 * </pre>
 * @see <a href="https://stackoverflow.com/questions/73256101/are-session-and-entitymanager-the-same">Are Session and EntityManager the same?</a>
 *
 *
 */
public class EntityManagerAndSessionLifeCycleTest {

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

    @AfterEach
    public void tearDown() {
        session.close();
        entityManager.close();
    }

    @DisplayName("SessionFactory | Session 생명주기")
    @RepeatedTest(2) // 2번 반복
    public void test() {
        System.out.println("sessionFactory.hashCode : " + sessionFactory.hashCode()); // 동일
        System.out.println("session.hashCode : " + session.hashCode()); // 매번 다름
    }

}