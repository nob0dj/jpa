package com.sh.entity.mapping._04.inheritance._02.joined._01.product.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("clothing")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
public class Clothing extends Product {
    private String size;
    private String material;

    public Clothing(Long id, String name, int price, String size, String material) {
        super(id, name, price);
        this.size = size;
        this.material = material;
    }
}