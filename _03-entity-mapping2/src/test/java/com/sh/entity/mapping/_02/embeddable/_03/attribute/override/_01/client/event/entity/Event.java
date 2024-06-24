package com.sh.entity.mapping._02.embeddable._03.attribute.override._01.client.event.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
        // name속성 : Client#code, Client#name
        // column속성 : 테이블 event.organizer_code, event.organizer_name
        @AttributeOverride(name = "code", column = @Column(name = "organizer_code")),
        @AttributeOverride(name = "name", column = @Column(name = "organizer_name")),
    })
    public Client organizer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "code", column = @Column(name = "participant_code")),
            @AttributeOverride(name = "name", column = @Column(name = "participant_name"))
    })
    public Client participant;
}