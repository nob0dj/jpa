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
    @AttributeOverride(name = "code", column = @Column(name = "organizer_code"))
    public Client organizer;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(name = "participant_code"))
    public Client participant;
}