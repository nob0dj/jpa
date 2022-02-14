package com.kh.spring.order.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders") // order는 sql 예약어이다.
@Data
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue
	private Long id;
	
	@ColumnDefault("systimestamp") // for DDL only 
	private Timestamp orderDate;
	
	@OneToMany(mappedBy = "order")
	private List<OrderProduct> orderProducts = new ArrayList<>();
	
	@PrePersist
	public void prePersist() {
		this.orderDate = this.orderDate == null ? new Timestamp(System.currentTimeMillis()) : orderDate;
	}

	
}
