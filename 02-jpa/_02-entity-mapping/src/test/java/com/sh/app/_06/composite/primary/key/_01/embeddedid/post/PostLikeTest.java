package com.sh.app._06.composite.primary.key._01.embeddedid.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

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
        create table post_like (
            created_at datetime(6),
            post_id bigint not null,
            user_id bigint not null,
            primary key (post_id, user_id)
        ) engine=InnoDB
     */
    @Test
    public void test() {
        //given
        PostLikeId postLikeId = new PostLikeId(1L, 123L);
        PostLike postLike = new PostLike(postLikeId, LocalDateTime.now());

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
        PostLike postLike2 = entityManager.find(PostLike.class, postLike.getId());
        assertThat(postLike2).isSameAs(postLike);
        assertThat(postLike2.getId()).isSameAs(postLike.getId());

    }
}
