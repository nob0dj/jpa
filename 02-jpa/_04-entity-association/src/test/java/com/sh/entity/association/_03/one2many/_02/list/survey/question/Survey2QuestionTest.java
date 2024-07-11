package com.sh.entity.association._03.one2many._02.list.survey.question;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Survey2QuestionTest {
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

    List<Question> questions = List.of(
            new Question("q1", "제일 좋아하는 컬러는?"),
            new Question("q2", "제일 좋아하는 음식는?"),
            new Question("q3", "제일 좋아하는 가수는?"),
            new Question("q4", "제일 무서워하는 것은?")
    );

    @Test
    @DisplayName("DDL 확인")
    void test() {
        /*
            Hibernate:
                create table survey (
                    id varchar(255) not null,
                    name varchar(255),
                    primary key (id)
                ) engine=InnoDB
            Jul 09, 2024 11:31:46 PM org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl getIsolatedConnection
            INFO: HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@5822ecda] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
            Hibernate:
                create table survey_question (
                    order_no integer,
                    id varchar(255) not null,
                    survey_id varchar(255),
                    title varchar(255),
                    primary key (id)
                ) engine=InnoDB
            Hibernate:
                alter table survey_question
                   add constraint FK573vicrl6b0e4d2jdg3wpvuhy
                   foreign key (survey_id)
                   references survey (id)
         */
    }

    @DisplayName("Survey-Question 등록")
    @Test
    public void test2() throws Exception {
        // given
        Survey survey = new Survey("s1", "취향존중검사", questions);
        // when
        questions.forEach(entityManager::persist);
        this.entityManager.persist(survey);
        System.out.println(survey);
        /*
        Hibernate:
            insert
            into
                survey_question
                (title, id)
            values
                (?, ?)
        Hibernate:
            insert
            into
                survey_question
                (title, id)
            values
                (?, ?)
        Hibernate:
            insert
            into
                survey_question
                (title, id)
            values
                (?, ?)
        Hibernate:
            insert
            into
                survey_question
                (title, id)
            values
                (?, ?)
        Hibernate:
            insert
            into
                survey
                (name, id)
            values
                (?, ?)
        Hibernate:
            update
                survey_question
            set
                survey_id=?,
                order_no=?
            where
                id=?
        Hibernate:
            update
                survey_question
            set
                survey_id=?,
                order_no=?
            where
                id=?
        Hibernate:
            update
                survey_question
            set
                survey_id=?,
                order_no=?
            where
                id=?
        Hibernate:
            update
                survey_question
            set
                survey_id=?,
                order_no=?
            where
                id=?
         */
        // then
        assertThat(survey.getQuestions()).isEqualTo(questions);
    }

    /**
     * <pre>
     * 0번지 요소를 삭제했을때...
     * 모든 survey_question 행별로 survey_id, order_no를 null로 update한 후
     * 새롭게 survey_id, order_no값을 update한다. (삭제한 survey_question행을 삭제하지는 않는다.)
     * List의 요소개수만치 update문이 수행되므로 효율적이라 할수 없다.
     * </pre>
     *
     * <img src="https://d.pr/i/imGZwj+">
     * @throws Exception
     */
    @DisplayName("Survey-Question 수정 (한건 삭제)")
    @Test
    public void test3() throws Exception {
        // given
        Survey survey = new Survey("s2", "개취검사", questions);
        questions.forEach(entityManager::persist);
        this.entityManager.persist(survey);
        this.entityManager.flush();
        System.out.println(survey);
        // when
        survey.removeQuestion(questions.get(0));
        this.entityManager.flush();
        /*
            Hibernate:
                update
                    survey_question
                set
                    survey_id=null,
                    order_no=null
                where
                    survey_id=?
                    and id=?
            Hibernate:
                update
                    survey_question
                set
                    survey_id=null,
                    order_no=null
                where
                    survey_id=?
                    and id=?
            Hibernate:
                update
                    survey_question
                set
                    survey_id=null,
                    order_no=null
                where
                    survey_id=?
                    and id=?
            Hibernate:
                update
                    survey_question
                set
                    survey_id=null,
                    order_no=null
                where
                    survey_id=?
                    and id=?
            Hibernate:
                update
                    survey_question
                set
                    survey_id=?,
                    order_no=?
                where
                    id=?
            Hibernate:
                update
                    survey_question
                set
                    survey_id=?,
                    order_no=?
                where
                    id=?
            Hibernate:
                update
                    survey_question
                set
                    survey_id=?,
                    order_no=?
                where
                    id=?
         */
        System.out.println(survey);
        // then
        assertThat(survey.getQuestions()).hasSize(3);
    }

    /**
     * <pre>
     * 모든 요소를 삭제했을때...
     * 모든 survey_question 행별로 survey_id, order_no를 null로 update한다.
     * 삭제한 survey_question행을 삭제(delete)하지는 않는다.
     * List의 요소개수만치 update문이 수행되므로 효율적이라 할수 없다.
     * </pre>
     * @throws Exception
     */
    @DisplayName("Survey-Question 수정 (모두 삭제)")
    @Test
    public void test4() throws Exception {
        // given
        Survey survey = new Survey("s3", "흥미진진검사", questions);
        questions.forEach(entityManager::persist);
        this.entityManager.persist(survey);
        this.entityManager.flush();
        System.out.println(survey);
        // when
        survey.removeAllQuestions();
        System.out.println(survey);
        /*
        Hibernate:
            update
                survey_question
            set
                survey_id=null,
                order_no=null
            where
                survey_id=?
         */
        // then
        assertThat(survey.getQuestions()).isEmpty();
    }

}