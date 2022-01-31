package com.kh.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;
import com.kh.spring.team.repository.TeamRepository;

@DataJpaTest
class HelloSpringJpaApplicationTests {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	
	@Test
	public void test() {
		Member member = Member.builder()
							.id("honggd")
							.password("1234")
							.build();
		member = memberRepository.save(member);
		System.out.println(member);
		assertThat("회원등록시 가입일s이 지정된다.", member.getEnrollDate(), is(notNullValue()));
		
	}

}
