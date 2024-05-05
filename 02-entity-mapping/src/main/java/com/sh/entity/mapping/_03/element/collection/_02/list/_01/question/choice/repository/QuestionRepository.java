package com.sh.entity.mapping._03.element.collection._02.list._01.question.choice.repository;

import com.sh.entity.mapping._03.element.collection._02.list._01.question.choice.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
