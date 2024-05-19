package com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public abstract class Payment {
    @Id
    // strategy = GenerationType.IDENTITY 사용불가 (각각 다른 테이블이 하나의 identity 컬럼을 공유할 수 없다)
    @GeneratedValue(strategy = GenerationType.AUTO) // GenerationType.TABLE로 처리
    private Long id;
    private int amount;
}