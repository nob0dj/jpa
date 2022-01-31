package com.kh.spring.member.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 실제로는 다음과 같이 상세히 관리된다.
 * - 광역지방자치단체	
 * - 기초지방자치단체	
 * - 시·군·구
 * - 읍·면
 * - 동·리	
 * - 번지
 * - 상세주소
 *
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	private String postCode;
	
	@Setter(AccessLevel.PRIVATE) // 불변객체 생성을 위해 setter는 private으로 생성
	private String address;
}
