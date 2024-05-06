package com.sh.entity.mapping._03.element.collection._02.list._02.question.choice.embeddable.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Choice {
    private String text;
    private boolean input;
}
