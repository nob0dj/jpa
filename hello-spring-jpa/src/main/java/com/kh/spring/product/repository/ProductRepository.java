package com.kh.spring.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
