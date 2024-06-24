package com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.repository;

import com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.entity.BankTransfer;
import com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.entity.CreditCard;
import com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.entity.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentRepositoryTest {
    @Autowired
    PaymentRepository paymentRepository;

    /*
        create table credit_card (
            amount integer not null,
            id bigint not null,
            card_number varchar(255),
            expiry_date varchar(255),
            primary key (id)
        ) engine=InnoDB

        create table bank_transfer (
            amount integer not null,
            id bigint not null,
            account_number varchar(255),
            bank_name varchar(255),
            primary key (id)
        ) engine=InnoDB
     */

    @DisplayName("CreditCard 등록")
    @Test
    public void test() throws Exception {
        // given
        CreditCard creditCard = new CreditCard(null, 35_000, "1234-5678-1234-5678", "26-11");
        // when
        creditCard = paymentRepository.save(creditCard);
        System.out.println(creditCard);
        // then
        assertThat(creditCard.getId()).isNotNull().isNotZero();
    }
    @DisplayName("BankTransfer 등록")
    @Test
    public void test2() throws Exception {
        // given
        BankTransfer bankTransfer = new BankTransfer(null, 50_000, "카카오뱅크", "1234567890");
        // when
        bankTransfer = paymentRepository.save(bankTransfer);
        System.out.println(bankTransfer);
        // then
        assertThat(bankTransfer.getId()).isNotNull().isNotZero();
    }

    @DisplayName("CreditCard, BankTransfer 조회")
    @Test
    @Rollback(false)
    void test3() {
        // given
        CreditCard creditCard = new CreditCard(null, 35_000, "1234-5678-1234-5678", "26-11");
        BankTransfer bankTransfer = new BankTransfer(null, 50_000, "카카오뱅크", "1234567890");
        paymentRepository.save(creditCard);
        paymentRepository.save(bankTransfer);
        // when
        List<Payment> all = paymentRepository.findAll();
        System.out.println(all);
        // then
        assertThat(all)
                .allSatisfy(payment -> {
                    assertThat(payment).satisfiesAnyOf(
                            p -> assertThat(p).isInstanceOf(CreditCard.class),
                            p -> assertThat(p).isInstanceOf(BankTransfer.class)
                    );
                });

    }
    /*
    select
        p1_0.id,
        p1_0.clazz_,
        p1_0.amount,
        p1_0.account_number,
        p1_0.bank_name,
        p1_0.card_number,
        p1_0.expiry_date
    from
        (select
            amount,
            id,
            account_number,
            bank_name,
            null as card_number,
            null as expiry_date,
            1 as clazz_
        from
            bank_transfer
        union
        all select
            amount,
            id,
            null as account_number,
            null as bank_name,
            card_number,
            expiry_date,
            2 as clazz_
        from
            credit_card
    ) p1_0
     */
}