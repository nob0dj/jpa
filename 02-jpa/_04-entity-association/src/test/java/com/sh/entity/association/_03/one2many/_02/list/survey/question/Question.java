package com.sh.entity.association._03.one2many._02.list.survey.question;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "survey_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Question {
    @Id
    private String id;
    private String title;
}
