package com.kh.spring.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.product.domain.Category;
import com.kh.spring.product.domain.CategoryName;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByCategoryName(CategoryName categoryName);
}
