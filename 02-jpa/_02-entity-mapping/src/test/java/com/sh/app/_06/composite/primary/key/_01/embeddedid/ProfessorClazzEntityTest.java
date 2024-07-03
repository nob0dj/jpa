package com.sh.app._06.composite.primary.key._01.embeddedid;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;


public class ProfessorClazzEntityTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    void setUp() {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        this.entityManager.close();
    }

    @AfterAll
    static void afterAll() {
        entityManagerFactory.close();
    }

    @Test
    @DisplayName("ddl-auto=create 확인")
    void test1() {
        /*
            create table tbl_professor_clazz (
                clazz_id bigint not null,
                professor_id bigint not null,
                classroom enum ('A','B','C'),
                primary key (clazz_id, professor_id)
            ) engine=InnoDB
         */
    }

    @Test
    @DisplayName("복합키를 사용한 엔티티 등록")
    void test2() {
        // given
        ProfessorClazzId professorClazzId = new ProfessorClazzId(100L, 200L);
        ProfessorClazz professorClazz = new ProfessorClazz(professorClazzId, Classroom.A);
        System.out.println(professorClazz);
        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(professorClazz);
            transaction.commit();
            /*
                insert
                into
                    tbl_professor_clazz
                    (classroom, clazz_id, professor_id)
                values
                    (?, ?, ?)
             */
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        // then
        entityManager.clear(); // 영속성컨텍스트 정리
        ProfessorClazz professorClazz2 = entityManager.find(ProfessorClazz.class, professorClazz.getId());
        /*
            select
                pc1_0.clazz_id,
                pc1_0.professor_id,
                pc1_0.classroom
            from
                tbl_professor_clazz pc1_0
            where
                (
                    pc1_0.clazz_id, pc1_0.professor_id
                ) in ((?, ?))
         */
        System.out.println(professorClazz2);
        assertThat(professorClazz2).isNotNull();
    }
}
