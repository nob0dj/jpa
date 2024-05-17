package com.sh.entity.association._03.one2many._02.list.survey.question.repository;

import com.sh.entity.association._03.one2many._02.list.survey.question.entity.Question;
import com.sh.entity.association._03.one2many._02.list.survey.question.entity.Survey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SurveyRepositoryTest {
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    QuestionRepository questionRepository;

    List<Question> questions = List.of(
            new Question("q1", "제일 좋아하는 컬러는?"),
            new Question("q2", "제일 좋아하는 음식는?"),
            new Question("q3", "제일 좋아하는 가수는?"),
            new Question("q4", "제일 무서워하는 것은?")
    );
    @BeforeEach
    void setUp() {
        questionRepository.saveAllAndFlush(questions);
    }

    @DisplayName("Survey-Quertion 등록")
    @Test
    public void test() throws Exception {
        // given
        Survey survey = new Survey("s1", "취향존중검사", questions);
        // when
        Survey survey2 = surveyRepository.saveAndFlush(survey);
        System.out.println(survey2);
        // then
        assertThat(survey2.getQuestions()).isEqualTo(questions);
    }

    /**
     * <pre>
     * 0번지 요소를 삭제했을때...
     * 모든 survey_question 행별로 survey_id, order_no를 null로 update한 후
     * 새롭게 survey_id, order_no값을 update한다. (삭제한 survey_question행을 삭제하지는 않는다.)
     * List의 요소개수만치 update문이 수행되므로 효율적이라 할수 없다.
     * </pre>
     * @throws Exception
     */
    @DisplayName("Survey-Question 수정 (한건 삭제)")
    @Test
    @Rollback(false)
    public void tes2() throws Exception {
        // given
        Survey survey = new Survey("s2", "개취검사", questions);
        survey = surveyRepository.saveAndFlush(survey);
        System.out.println(survey);
        // when
        survey.removeQuestion(questions.get(0));
        survey = surveyRepository.saveAndFlush(survey);
        System.out.println(survey);
        // then
        assertThat(survey.getQuestions()).hasSize(3);
    }

    /**
     * <pre>
     * 0번지 요소를 삭제했을때...
     * 모든 survey_question 행별로 survey_id, order_no를 null로 update한다.
     * 삭제한 survey_question행을 삭제(delete)하지는 않는다.
     * List의 요소개수만치 update문이 수행되므로 효율적이라 할수 없다.
     * </pre>
     * @throws Exception
     */
    @DisplayName("Survey-Question 수정 (모두 삭제)")
    @Test
    @Rollback(false)
    public void tes3() throws Exception {
        // given
        Survey survey = new Survey("s3", "흥미진진검사", questions);
        survey = surveyRepository.saveAndFlush(survey);
        System.out.println(survey);
        // when
        survey.removeAllQuestions();
        survey = surveyRepository.saveAndFlush(survey);
        System.out.println(survey);
        // then
        assertThat(survey.getQuestions()).isEmpty();
    }

}