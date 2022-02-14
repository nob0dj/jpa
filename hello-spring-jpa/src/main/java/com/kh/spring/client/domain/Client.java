package com.kh.spring.client.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.kh.spring.laptop.domain.Laptop;
import com.kh.spring.room.domain.Room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client", uniqueConstraints = {
		@UniqueConstraint(name = "uq_client_laptop_id", columnNames = {"laptop_id"})
		})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	/**
	 * 일대일 - 주테이블에 외래키
	 */
	@OneToOne
	@JoinColumn(name = "laptop_id")
	private Laptop laptop;
	
	public void setLaptop(Laptop laptop) {
		System.out.println(this.laptop);
		
		// 기존 laptop에서 member제거
		if(this.laptop != null)
			if(laptop != null && this.laptop != laptop) // 두개의 Client가 하나의 Laptop을 참조하려는 경우, StackOverflowError 대비
			this.laptop.setClient(null);

		this.laptop = laptop;
		
		// Laptop#member 설정 - 무한루프 방지
		if(laptop != null && laptop.getClient() != this ) {
			laptop.setClient(this);
		}
	}
	
	/**
	 * 일대일 - 대상테이블에 외래키 
	 * 양방향만 가능
	 */
	@OneToOne(mappedBy="client")
	private Room room;
	
	public void setRoom(Room room) {
		if(this.room != null)
			this.room.setClient(null);
		
		this.room = room;
		
		if(room != null && room.getClient() != this)
			room.setClient(this);
	}

}
