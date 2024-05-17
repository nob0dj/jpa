package com.sh.entity.association._03.one2many._02.list.survey.question.repository;

import com.sh.entity.association._03.one2many._02.list.survey.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, String> {
}
