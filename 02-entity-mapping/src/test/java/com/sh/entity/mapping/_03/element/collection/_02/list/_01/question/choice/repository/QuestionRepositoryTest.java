package com.sh.entity.mapping._03.element.collection._02.list._01.question.choice.repository;

import com.sh.entity.mapping._03.element.collection._02.list._01.question.choice.entity.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

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

    @DisplayName("Question 보기 수정")
    @Test
    public void test2() throws Exception {
        // given
        // when
        // then
    }

}