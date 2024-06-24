package com.sh.entity.mapping._03.element.collection._03.map._01.document.prop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "doc")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @Setter
    @ElementCollection
    @CollectionTable(
        name = "doc_prop",
        joinColumns = @JoinColumn(name = "doc_id")
    )
    @MapKeyColumn(name = "name") // doc_prop테이블 컬럼명
    @Column(name = "value") // doc_prop테이블 컬럼명
    private Map<String, String> props = new HashMap<>();

    public void removeProp(String name) {
        props.remove(name);
    }
}
