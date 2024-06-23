package com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
public class BankTransfer extends Payment {
    private String bankName;
    private String accountNumber;

    public BankTransfer(Long id, int amount, String bankName, String accountNumber) {
        super(id, amount);
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
}