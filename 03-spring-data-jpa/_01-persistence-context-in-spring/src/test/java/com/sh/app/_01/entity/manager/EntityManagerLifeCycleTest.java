package com.sh.app._01.entity.manager;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO 테스트메소드별로 EntityManager가 동일하다. EntityManager 객체의 생명주기가 request-scope 가 아니다?
 *
 *
 */
@SpringBootTest
public class EntityManagerLifeCycleTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private EntityManager entityManager;

    @DisplayName("EntityManagerFactory | EntityManger 생명주기")
    @RepeatedTest(2) // 2번 반복
    public void test() {
        System.out.println("테스트클래스 객체 : " + this.hashCode());
        System.out.println("entityManagerFactory.hashCode : " + entityManagerFactory.hashCode());
        System.out.println("entityManager.hashCode : " + entityManager.hashCode());
    }
    /*
        (1)
        테스트클래스 객체 : 79120973
        entityManagerFactory.hashCode : 1287200676
        entityManager.hashCode : 276741549

        (2)
        테스트클래스 객체 : 1533639059
        entityManagerFactory.hashCode : 1287200676
        entityManager.hashCode : 276741549
     */

}