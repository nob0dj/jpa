package com.sh.entity.mapping._03.element.collection._02.list._01.question.choice.repository;

import com.sh.entity.mapping._03.element.collection._02.list._01.question.choice.entity.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    /*
        create table question (
            id bigint not null auto_increment,
            text varchar(255),
            primary key (id)
        ) engine=InnoDB

        create table question_choice (
            idx integer not null,
            question_id bigint not null,
            text varchar(255),
            primary key (idx, question_id)
        ) engine=InnoDB
     */
    @DisplayName("Question 등록")
    @Test
    public void test() throws Exception {
        // given
        List<String> choices = List.of("DQL", "DDL", "DHL", "DML");
        Question question = new Question(null, "다음중 SQL의 종류가 아닌것은?", choices);
        // when
        questionRepository.saveAndFlush(question);
        System.out.println(question);
        // then
        assertThat(question.getId()).isNotNull().isNotZero();
    }

    /**
     * 보통 List객체의 일부를 수정하지 않고, 전체를 교체한다.
     * 이때, List.of()로 생성한 immutalble Listr객체를 사용하면, java.lang.UnsupportedOperationException 발생한다.
     * @throws Exception
     */
    @DisplayName("Question 보기 수정")
    @Test
    public void test2() throws Exception {
        // given
//        List<String> choices = List.of("DQL", "DDL", "DHL", "DML");
        List<String> choices = new ArrayList<>();
        choices.add("DQL");
        choices.add("DDL");
        choices.add("DHL");
        choices.add("DML");
        Question question = new Question(null, "다음중 SQL의 종류가 아닌것은?", choices);
        questionRepository.saveAndFlush(question);
        // when
//        List<String> choices2 = List.of("dql", "ddl", "dhl", "dml");
        List<String> choices2 = new ArrayList<>();
        choices2.add("dql");
        choices2.add("ddl");
        choices2.add("dhl");
        choices2.add("dml");
        question.setChoices(choices2);
        questionRepository.saveAndFlush(question);
        // then
        assertThat(question.getChoices()).usingRecursiveComparison().isEqualTo(choices2);
    }

}