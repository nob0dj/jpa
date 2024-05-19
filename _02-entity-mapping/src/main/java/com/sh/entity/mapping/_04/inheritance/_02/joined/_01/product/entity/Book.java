package com.sh.entity.mapping._04.inheritance._02.joined._01.product.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@DiscriminatorValue("book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
public class Book extends Product {
    private String author;
    private String isbn;

    public Book(Long id, String name, int price, String author, String isbn) {
        super(id, name, price);
        this.author = author;
        this.isbn = isbn;
    }
}