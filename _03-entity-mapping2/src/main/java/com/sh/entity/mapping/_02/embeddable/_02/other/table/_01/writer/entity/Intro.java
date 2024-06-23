package com.sh.entity.mapping._02.embeddable._02.other.table._01.writer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Intro {
    @Column(table = "writer_intro", name = "content_type")
    private String contentType;
    @Column(table = "writer_intro")
    private String content;
}
