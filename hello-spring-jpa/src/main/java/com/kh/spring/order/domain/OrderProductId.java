package com.kh.spring.order.domain;

import java.io.Serializable;

import com.kh.spring.product.domain.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 복합키를 사용하기1 - @IdClass
 * - @IdClass에 해당하는 클래스를 생성해야 복합키 사용이 가능하다. 
 * - Serializable 구현
 * - equals, hashCode override
 * - 기본생성장
 * - 식별자 클래스는 public
 * 
 * @IdClass외에 @EmbeddedId를 통해 복합키 사용이 가능하다.
 *
 */
@Data
@NoArgsConstructor
public class OrderProductId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long order; // Order@id 타입 - OrderProduct#order 필드명 
	private Long product;
	
}
