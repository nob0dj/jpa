package com.sh.entity.mapping._04.inheritance._02.joined._01.product.repository;

import com.sh.entity.mapping._04.inheritance._02.joined._01.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
