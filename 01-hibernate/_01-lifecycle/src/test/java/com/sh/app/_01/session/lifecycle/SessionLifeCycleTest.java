package com.sh.app._01.session.lifecycle;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

/**
 * <pre>
 * java진영 orm규격 jpa소속 인터페이스. 모든 java orm구현체는 이 규격을 준수해야 한다.
 * - jakarta.persistence.EntityManager;
 * - jakarta.persistence.EntityManagerFactory;
 * - 설정파일은 src/resources/META-INF/persistence.xml
 *
 * 구현체인 hibernate 소속 인터페이스. eclipselink등 다른 orm을 사용하는 경우 달라질 수 있다.
 * - org.hibernate.Session;
 * - org.hibernate.SessionFactory;
 * - 설정파일은 src/resources/hibernate.cfg.xml
 *
 * </pre>
 *
 * @see <a href="https://stackoverflow.com/questions/73256101/are-session-and-entitymanager-the-same">Are Session and EntityManager the same?</a>
 */
public class SessionLifeCycleTest {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void beforeAll() {
        String resourceName = "hibernate.cfg.xml";
        Configuration config = new Configuration();
        // resourceName이 기본값인 경우 생략가능
        config.configure(resourceName);
        sessionFactory = config.buildSessionFactory();
    }

    @BeforeEach
    public void setUp() {
        session = sessionFactory.openSession();
    }

    @AfterAll
    public static void afterAll() {
        sessionFactory.close();
    }

    @AfterEach
    public void tearDown() {
        session.close();
    }

    @DisplayName("SessionFactory | Session 생명주기")
    @RepeatedTest(2) // 2번 반복
    public void test() {
        System.out.println("sessionFactory.hashCode : " + sessionFactory.hashCode()); // 동일
        System.out.println("session.hashCode : " + session.hashCode()); // 매번 다름
    }

}