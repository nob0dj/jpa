package com.sh.entity.association._03.one2many._02.list.survey.question;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "survey")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Survey {
    @Id
    private String id;
    private String name;
    @OneToMany
    @JoinColumn(name = "survey_id")
    @OrderColumn(name = "order_no")
    private List<Question> questions = new ArrayList<>();

    public Survey(String id, String name, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = new ArrayList<>(questions);
    }

    public void addQuestion(Question q) {
        this.questions.add(q);
    }

    public void removeQuestion(Question q) {
        this.questions.remove(q);
    }

    public void removeAllQuestions() {
//        questions.clear();
        this.questions = null;
    }
}
