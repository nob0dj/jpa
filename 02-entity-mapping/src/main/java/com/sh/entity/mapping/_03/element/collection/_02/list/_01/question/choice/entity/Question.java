package com.sh.entity.mapping._03.element.collection._02.list._01.question.choice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "question_choice",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @OrderColumn(name = "idx")
    @Column(name = "text") // question_choice테이블 text 컬럼
    @Setter
    private List<String> choices = new ArrayList<>();
}
