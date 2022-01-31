package com.kh.spring;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.spring.product.domain.Category;
import com.kh.spring.product.domain.CategoryName;
import com.kh.spring.product.domain.Product;
import com.kh.spring.product.repository.CategoryRepository;
import com.kh.spring.product.repository.ProductRepository;

/**
 * 
 * @ManyToMany 사용 클래스
 * - Product
 * - Category
 * 
 *
 */
//@RunWith(Runner.class)
@SpringBootTest
class ProductCategoryRepositoryTest {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Test
	@DisplayName("Category등록")
	void test1() {
		// given
		CategoryName[] categoryNameValues = CategoryName.values();
		for(CategoryName categoryName : categoryNameValues) {
			Category category = new Category(categoryName);
			categoryRepository.save(category);
		}
		
		// when
		List<Category> categories = categoryRepository.findAll();
		
		// then
		assertEquals(CategoryName.values().length, categories.size());
		for(int i = 0; i < categoryNameValues.length; i++)
			assertEquals(categoryNameValues[i], categories.get(i).getCategoryName());
	}
	
	@Test
	@Transactional
	@DisplayName("Product등록")
	void test2() {
		// given
		Category category1 = categoryRepository.findByCategoryName(CategoryName.CLOCK);
		Category category2 = categoryRepository.findByCategoryName(CategoryName.CUP);
		
		Product product = new Product("TimeFlowsCup");
		product.getCategories().add(category1);
		product.getCategories().add(category2);
		productRepository.save(product);
		
		// when 
		Product p = productRepository.findById(product.getId()).get();
		
		// then
		System.out.println(p);
		assertThat("p.categories.size == 2", p.getCategories().size(), is(equalTo(2)));
		assertTrue(p.getCategories().contains(category1));
		assertTrue(p.getCategories().contains(category2));
	}
	
	@Test
	@Transactional
	@DisplayName("양방향 테스트")
	void test3() {
		// given
		Category category = categoryRepository.findByCategoryName(CategoryName.CLOCK);
		
		Product product1 = new Product("TimeFlowsCup");
//		product1.getCategories().add(category);
//		category.getProducts().add(product1);
		product1.addCategory(category);
		productRepository.save(product1);
		
		Product product2 = new Product("MinimalCementClock");
//		product2.getCategories().add(category);
//		category.getProducts().add(product2);
		product2.addCategory(category);
		productRepository.save(product2);
		
		// when
		Category c = categoryRepository.findByCategoryName(CategoryName.CLOCK);
		List<Product> products = c.getProducts();

		// then
		assertEquals(2, products.size());
		assertTrue(products.contains(product1));
		assertTrue(products.contains(product2));
		
		
		
	}
	

}
