package com.sh.app._02.entity.manager.lifecycle;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
public class EntityManagerLifeCycleTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @DisplayName("EntityManagerFactory | EntityManger 생명주기")
    @RepeatedTest(2) // 2번 반복
    public void test() {
        System.out.println("entityManagerFactory.hashCode : " + entityManagerFactory.hashCode());
        System.out.println("entityManager.hashCode : " + entityManager.hashCode());
    }

}