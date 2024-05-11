package com.sh.entity.mapping._03.element.collection._03.map._02.document.prop.embeddable.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PropValue {
    private String value;
    private boolean enabled;
}
