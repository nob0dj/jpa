package com.sh.entity.mapping._04.inheritance._02.joined._01.product.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <pre>
 * 식별관계의 product-book, product-clothing 테이블 생성
 * </pre>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
}
