package com.kh.spring;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Date;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.member.domain.Gender;
import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED) // 자동롤백 하지 않음 
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 db사용
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@DisplayName("회원객체 저장 테스트")
	@Rollback(false)
	void test1() {
		// given
		Member member = Member.builder()
							.id("honggd")
							.password("2345")
							.name("홍길동")
							.gender(Gender.M)
							.birthday(Date.valueOf("1988-08-08"))
//							.hobby(new String[] {"음악", "영화", "독서"})
							.hobby(Arrays.asList(new String[] {"음악", "영화", "독서"}))
							.build();
		
		// when
		member = memberRepository.save(member); // 리턴 필수
		
		// then
		assertThat("회원가입시 등록일 enrollDate 필드는 @PrePersist에 의해 new Date()로 처리되어야 한다.", member.getEnrollDate(), is(notNullValue()));
		assertThat("회원가입시 등록일 enabled 필드는 @PrePersist에 의해 true 로 처리되어야 한다.", member.getEnabled(), is(true));
		
		
		// when
		Member _member = memberRepository.findById("honggd").orElseThrow();
		
//		System.out.println(System.identityHashCode(member) + " " +  member); 	// 1660093884
//		System.out.println(System.identityHashCode(_member) + " " + _member); 	// 1660093884
		
		// then
		assertThat("영속성컨텍스트는 entity 동등성 보장!", _member, is(equalTo(member)));
		assertThat("동일한 객체인가", _member, is(sameInstance(member)));
		
	}
	
	@Test
	@DisplayName("회원객체 저장 테스트")
//	@Rollback(false)
	void test2() {
		
		// when
		memberRepository.deleteById("honggd");
		
		// then
		Member member = memberRepository.findById("honggd").orElse(null);
		assertThat("조회된 honggd enttiy 없음", member, is(nullValue()));

	}

}
