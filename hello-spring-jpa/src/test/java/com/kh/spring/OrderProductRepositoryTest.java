package com.kh.spring;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.kh.spring.order.domain.Order;
import com.kh.spring.order.domain.OrderProduct;
import com.kh.spring.order.domain.OrderProductId;
import com.kh.spring.order.repository.OrderProductRepository;
import com.kh.spring.order.repository.OrderRepository;
import com.kh.spring.product.domain.Category;
import com.kh.spring.product.domain.CategoryName;
import com.kh.spring.product.domain.Product;
import com.kh.spring.product.repository.CategoryRepository;
import com.kh.spring.product.repository.ProductRepository;


//@RunWith(Runner.class) // JUnit4/5 의존
@SpringBootTest
class OrderProductRepositoryTest {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderProductRepository orderProductRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Test
	@DisplayName("단방향 주문상품에서 주문과 상품 조회")
	@Transactional
	@Rollback(false)
	void test1() {
		// given
		Order order = new Order();
		orderRepository.save(order);
		
		Category category = new Category(CategoryName.DESKTOP);
		categoryRepository.save(category);
		
		Product product = new Product("전동책상");
		product.addCategory(category);
		productRepository.save(product);
		
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setOrder(order);
		orderProduct.setProduct(product);
		orderProduct.setProductAmount(5);
		orderProductRepository.save(orderProduct);
		
		// when
		OrderProductId orderProductId = new OrderProductId();
		orderProductId.setOrder(order.getId());
		orderProductId.setProduct(product.getId());
		OrderProduct _orderProduct = orderProductRepository.findById(orderProductId).get();
		
		// then
		/*
		 * @Transactional을 통해 entity의 필드 내부참조 proxy의 equals비교가 아닌 
		 * 초기화를 통해 실제 db값 비교가 이루어져야 아래 단정문이 참이된다.
		 * 
		 * OrderProduct
		 *  └ Order
		 *  └ Product
		 *  	└ List<Category>
		 *  		└ List<Product>
		 */
		assertThat("_orderProduct(조회) eq orderProduct(저장)", orderProduct, is(equalTo(_orderProduct)));
		assertEquals(order, _orderProduct.getOrder());
		assertEquals(product, _orderProduct.getProduct());
	}
}

