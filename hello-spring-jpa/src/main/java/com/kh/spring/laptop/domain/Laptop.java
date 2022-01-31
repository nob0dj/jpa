package com.kh.spring.laptop.domain;

import java.io.Serializable;
import java.util.Random;
import java.util.stream.IntStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import com.kh.spring.member.domain.Member;

import lombok.Data;

@Entity
@Data
public class Laptop implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 15, nullable = false)
	private String serialNumber;
	
	/**
	 * 양방향으로 추가
	 */
	@OneToOne(mappedBy = "laptop")
	private Member member;
	
	
	
	
	@PrePersist
	public void prePersist() {
		Random rnd = new Random();
		this.serialNumber =  
			IntStream.range(0, 15)
				.mapToObj(n -> String.valueOf(rnd.nextInt(10)))
				.reduce("", (acc, next) -> acc + next);
	}


	@Override
	public String toString() {
		return "Laptop [id=" + id + ", serialNumber=" + serialNumber + ", member=" + (member != null ? member.getId() : member) + "]";
	}


	
}
