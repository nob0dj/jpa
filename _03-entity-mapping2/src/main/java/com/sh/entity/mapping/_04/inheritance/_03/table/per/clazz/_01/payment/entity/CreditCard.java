package com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
public class CreditCard extends Payment {
    private String cardNumber;
    private String expiryDate;

    public CreditCard(Long id, int amount, String cardNumber, String expiryDate) {
        super(id, amount);
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }
}

