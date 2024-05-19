package com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.repository;

import com.sh.entity.mapping._04.inheritance._03.table.per.clazz._01.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
