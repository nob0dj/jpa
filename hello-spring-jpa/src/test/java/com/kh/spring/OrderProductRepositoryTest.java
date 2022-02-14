package com.kh.spring;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.kh.spring.category.domain.Category;
import com.kh.spring.category.domain.CategoryName;
import com.kh.spring.category.repository.CategoryRepository;
import com.kh.spring.order.domain.Order;
import com.kh.spring.order.domain.OrderProduct;
import com.kh.spring.order.domain.OrderProductId;
import com.kh.spring.order.repository.OrderProductRepository;
import com.kh.spring.order.repository.OrderRepository;
import com.kh.spring.product.domain.Product;
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
		
		// when : @IdClass를 사용해 조회
		OrderProductId orderProductId = new OrderProductId();
		orderProductId.setOrder(order.getId());
		orderProductId.setProduct(product.getId());
		OrderProduct _orderProduct = orderProductRepository.findById(orderProductId).get();
		System.out.println("_orderProduct = " + _orderProduct); // OrderProduct(order=Order(id=1, orderDate=2022-02-13 22:59:49.632), product=Product(id=3, name=전동책상, categories=[Category(id=2, categoryName=DESKTOP)]), productAmount=5)
		
		// then
		/*
		 * @Transactional을 사용해야 한다.
		 * entity의 지연로딩용 proxy필드(OrderProduct#order, OrderProduct#product)가 초기화되어야 한다. 
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
	
	@Transactional
	@Test
	@DisplayName("양방향 주문에서 주문상품목록 조회")
	@Rollback(false)
	void test2() {
		// given
		Order order = new Order();
		orderRepository.save(order);
		
		Product product1 = new Product("좌식테이블");
		productRepository.save(product1);
		Product product2 = new Product("스툴");
		productRepository.save(product2);
		
		OrderProduct orderProduct1 = new OrderProduct();
		orderProduct1.setOrder(order);
		orderProduct1.setProduct(product1);
		orderProduct1.setProductAmount(5);
		orderProductRepository.save(orderProduct1);
		
		OrderProduct orderProduct2 = new OrderProduct();
		orderProduct2.setOrder(order);
		orderProduct2.setProduct(product2);
		orderProduct2.setProductAmount(3);
		orderProductRepository.save(orderProduct2);
		
		// when
		Order o = orderRepository.findById(order.getId()).get();
		List<OrderProduct> orderProducts = o.getOrderProducts();
		
		// then
		System.out.println(o); // Order(id=4, orderDate=2022-02-13 23:11:42.768, orderProducts=[OrderProduct [order=4, product=5, productAmount=5], OrderProduct [order=4, product=6, productAmount=3]])
		assertEquals(2, orderProducts.size());
		assertEquals(product1, orderProducts.get(0).getProduct());
		assertEquals(product2, orderProducts.get(1).getProduct());
	}
}

