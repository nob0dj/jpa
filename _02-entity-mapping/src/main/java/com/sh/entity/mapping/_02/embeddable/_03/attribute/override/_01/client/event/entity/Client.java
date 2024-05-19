package com.sh.entity.mapping._02.embeddable._03.attribute.override._01.client.event.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Client {
    public Long code;
}