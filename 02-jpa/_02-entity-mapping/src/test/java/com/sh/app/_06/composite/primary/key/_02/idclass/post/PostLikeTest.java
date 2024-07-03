package com.sh.app._06.composite.primary.key._02.idclass.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class PostLikeTest {
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

    /*
        create table tbl_post_like0602 (
            post_id bigint not null,
            user_id bigint not null,
            primary key (post_id, user_id)
        ) engine=InnoDB
     */
    @Test
    public void test() {
        //given
        PostLike postLike = new PostLike();
        postLike.setPostId(1L);
        postLike.setUserId(123L);

        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(postLike);
        entityTransaction.commit();
        /*
            insert
            into
                post_like
                (created_at, post_id, user_id)
            values
                (?, ?, ?)
         */
        //then
        PostLike postLike2 = entityManager.find(PostLike.class, new PostLikeId(1L, 123L));
        System.out.println(postLike);

    }
}
