package com.sh.entity.mapping._03.element.collection._02.list._02.question.choice.embeddable.repository;

import com.sh.entity.mapping._03.element.collection._02.list._02.question.choice.embeddable.entity.Choice;
import com.sh.entity.mapping._03.element.collection._02.list._02.question.choice.embeddable.entity.Question2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED) // 트랜잭션사용안함. 자동롤백하지 않음(실제 db반영)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Question2RepositoryTest {

    @Autowired
    Question2Repository question2Repository;

    Choice[] choices = {
            new Choice("지구는 둥글다.", true),
            new Choice("물은 원래 무색투명하다", true),
            new Choice("새는 아가미로 호흡한다.", false),
            new Choice("소는 게으르다", true)
    };

    @DisplayName("Question-Choice 등록")
    @Test
    public void test() throws Exception {
        // given
        Question2 question = new Question2(null, "다음 각 문자의 참거짓을 작성하세요.", List.of(choices));
        // when
        question2Repository.saveAndFlush(question);
        // then
        assertThat(question.getId()).isNotNull();
    }

    @DisplayName("Question 조회")
    @Test
    public void test2() throws Exception {
        // given
        Long id = 1L;
        // when
        Question2 question = question2Repository.findById(id).get();
        System.out.println(question);
        // then
        assertThat(question.getChoices())
                .isNotNull()
                .isNotEmpty()
                .containsExactly(choices);
    }
}