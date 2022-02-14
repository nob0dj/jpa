package com.kh.spring.follow.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Embeddable 클래스 작성방법
 * - Serializable 구현
 * - equals, hashCode override
 * - 기본생성자
 * - 식별자 클래스는 public이어야 한다. 
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String followee; // AppUser#id 타입
 	
	private String follower; // AppUser#id 타입
}
