package com.kh.spring.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
