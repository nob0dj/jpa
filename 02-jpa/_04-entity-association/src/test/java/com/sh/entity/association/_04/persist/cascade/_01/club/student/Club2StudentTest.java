package com.sh.entity.association._04.persist.cascade._01.club.student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;


class Club2StudentTest {
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
            create table club (
                id varchar(255) not null,
                name varchar(255),
                primary key (id)
            ) engine=InnoDB

            create table student (
                club_id varchar(255),
                id varchar(255) not null,
                name varchar(255),
                primary key (id)
            ) engine=InnoDB

            alter table student
               add constraint FKckcg1048dky4je7i86wk7dreh
               foreign key (club_id)
               references club (id)
         */
    }

    @DisplayName("Club - Student 영속성전이")
    @Test
    public void test2() throws Exception {
        // given
        Student student1 = new Student("s1", "홍길동");
        Student student2 = new Student("s2", "신사임당");
        Club club = new Club("c1", "오륙도노래동아리", Set.of(student1, student2));
        // when
        this.entityManager.persist(club);
        this.entityManager.flush();
        // then
        assertThat(this.entityManager.find(Student.class, student1.getId())).isNotNull();
        assertThat(this.entityManager.find(Student.class, student2.getId())).isNotNull();

        assertThatNoException().isThrownBy(() -> System. out. println("OK"));
        assertThatCode(() -> System. out. println("OK")).doesNotThrowAnyException();
    }

}