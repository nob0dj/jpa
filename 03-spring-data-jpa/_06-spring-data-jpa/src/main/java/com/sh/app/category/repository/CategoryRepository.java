package com.sh.app.category.repository;

import com.sh.app.category.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByRefCategoryCodeIsNotNull(Sort sort);
}
