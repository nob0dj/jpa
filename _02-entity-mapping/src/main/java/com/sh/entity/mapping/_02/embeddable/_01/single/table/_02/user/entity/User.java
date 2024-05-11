package com.sh.entity.mapping._02.embeddable._01.single.table._02.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Embedded
    private Address homeAddress;

    @AttributeOverrides({
            // name=Address클래스필드명 <-> column=DB컬럼명
            @AttributeOverride(name = "address1", column = @Column(name = "work_addr1")),
            @AttributeOverride(name = "address2", column = @Column(name = "work_addr2")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode"))
    })
    @Embedded
    private Address workAddress;
}
