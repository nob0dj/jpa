package com.kh.spring.order.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kh.spring.product.domain.Product;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@IdClass(OrderProductId.class) // 복합키 사용을 위한 식별자 클래스 필수
public class OrderProduct {

	@Id
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order; // OrderProductId#order와 연결
	
	public void setOrder(Order order) {
		this.order = order;
		if(order != null && !order.getOrderProducts().contains(this))
			order.getOrderProducts().add(this);
	}
	
	@Id
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product; // OrderProductId#product와 연결
	
	private int productAmount;

	@Override
	public String toString() {
		return "OrderProduct [order=" + order.getId() + ", product=" + product.getId() + ", productAmount=" + productAmount + "]";
	}
	
	
}
