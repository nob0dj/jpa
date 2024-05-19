package com.sh.entity.mapping._02.embeddable._02.other.table._01.writer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "writer")
@SecondaryTables({
        @SecondaryTable(name = "writer_address",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "writer_id", referencedColumnName = "id")
        ),
        @SecondaryTable(name = "writer_intro",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "writer_id", referencedColumnName = "id")
        )}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Embedded
    private Intro intro;

    /**
     * <pre>
     * - @AttributeOverride(name) embedded 클래스의 field명
     * - @AttributeOverride(column = @Column(table, name)) db테이블, column명
     * </pre>
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address1", column = @Column(table = "writer_address", name = "addr1")),
            @AttributeOverride(name = "address2", column = @Column(table = "writer_address", name = "addr2")),
            @AttributeOverride(name = "zipcode", column = @Column(table = "writer_address"))
    })
    private Address address;



}
