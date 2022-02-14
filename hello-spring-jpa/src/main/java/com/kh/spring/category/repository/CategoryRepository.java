package com.kh.spring.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.category.domain.Category;
import com.kh.spring.category.domain.CategoryName;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByCategoryName(CategoryName categoryName);
}
