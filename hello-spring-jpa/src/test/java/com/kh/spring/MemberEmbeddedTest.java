package com.kh.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.kh.spring.carrier.domain.Carrier;
import com.kh.spring.carrier.repository.CarrierRepository;
import com.kh.spring.member.domain.Address;
import com.kh.spring.member.domain.Gender;
import com.kh.spring.member.domain.Member;
import com.kh.spring.member.domain.Phone;
import com.kh.spring.member.domain.TelecomCompany;
import com.kh.spring.member.repository.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberEmbeddedTest {

	@Autowired
	MemberRepository memberRepository;
	
	
	@DisplayName("@Embbeded타입 @ElementCollection타입 등록")
	@Test
	@Rollback(false)
	void test1() {
		// given
		Carrier kt = new Carrier(TelecomCompany.KT); // Phone -> Carrier 영속성전이 CascadeType.ALL
		
		Member member = Member.builder()
							.id("honggd")
							.password("2345")
							.name("홍길동")
							.gender(Gender.M)
							.birthday(Date.valueOf("1988-08-08"))
//							.hobby(new String[] {"음악", "영화", "독서"})
							.hobby(Arrays.asList(new String[] {"음악", "영화", "독서"}))
							.address(new Address("08345", "서울시 강남구 역삼동"))
							.phone(new Phone(kt, "01012341234"))
							.build();
		memberRepository.save(member); // 리턴 필수
		
		// when
		Member m = memberRepository.findById("honggd").get();
		System.out.println(m);
		
		// then
		
	}
	
	@DisplayName("@Embbeded타입 @ElementCollection타입 조회")
	@Test
	void test2() {
		// when
		Member m = memberRepository.findById("honggd").get();
		System.out.println(m);
		
	}
	
	@DisplayName("@Embbeded타입 @ElementCollection타입 수정")
	@Rollback(false)
	@Test
	void test3() {
		// given
		Member honggd = memberRepository.findById("honggd").get();
		
		// when
		// 값은 불변해야 한다.
		// 값타입의 수정은 객체를 삭제후 재지정한다.
		Carrier sk = new Carrier(TelecomCompany.SK); // CascadeType.ALL
		String NEW_PHONE_NUMBER = "01033334444";
		Phone phone = new Phone(sk, NEW_PHONE_NUMBER);
		honggd.setPhone(phone);
		
		String NEW_POST_CODE = "11111";
		String NEW_ADDRESS = "서울시 강동구 으르렁동";
		Address address = new Address(NEW_POST_CODE, NEW_ADDRESS);
		honggd.setAddress(address);
		
		List<String> hobby = honggd.getHobby();
		hobby.remove("영화");
		hobby.remove("음악");
		String NEW_HOBBY_SCUBA_DIVING = "스쿠버다이빙";
		hobby.add(NEW_HOBBY_SCUBA_DIVING);
		
		memberRepository.save(honggd);
		
		// then
		Member m = memberRepository.findById("honggd").get();
		assertEquals(TelecomCompany.SK, m.getPhone().getCarrier().getTelecomCompany());
		assertEquals(NEW_PHONE_NUMBER, m.getPhone().getPhoneNumber());
		
		assertEquals(NEW_POST_CODE, m.getAddress().getPostCode());
		assertEquals(NEW_ADDRESS, m.getAddress().getAddress());
		
		assertTrue(m.getHobby().contains(NEW_HOBBY_SCUBA_DIVING));
		assertEquals(2, m.getHobby().size());
		
		
	}
	
	
	@DisplayName("@Embbeded타입 @ElementCollection타입 삭제")
	@Rollback(false)
	@Test
	void test4() {
		// given
		Member honggd = memberRepository.findById("honggd").get();
		
		// when
		honggd.setAddress(null);
		honggd.setPhone(null);
		honggd.setHobby(null);
		
		memberRepository.save(honggd);
		
		// then
		Member m = memberRepository.findById("honggd").get();
		assertNull(m.getPhone());
		assertNull(m.getAddress());
		
		assertNull(m.getHobby());
//		assertThat("hobby의 길이는 0이다.", m.getHobby(), is(empty()));
		
		
	}

}
