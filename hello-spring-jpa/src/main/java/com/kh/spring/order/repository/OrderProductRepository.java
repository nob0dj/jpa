package com.kh.spring.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.order.domain.OrderProduct;
import com.kh.spring.order.domain.OrderProductId;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

}
