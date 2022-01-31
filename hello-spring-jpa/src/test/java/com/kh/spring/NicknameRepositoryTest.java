package com.kh.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;
import com.kh.spring.nickname.domain.Nickname;
import com.kh.spring.nickname.repository.NicknameRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) 
class NicknameRepositoryTest {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	NicknameRepository nicknameRepository;

	@DisplayName("회원별칭 저장")
	@Test
	void test() {
		// given
		Member honggd = Member.builder()
							.id("honggd")
							.password("2345")
							.name("홍길동")
							.build();
		honggd = memberRepository.save(honggd);
		
		final String HONGGD_NICKNAME = "홍길동구리";
		Nickname nickname = new Nickname(honggd, HONGGD_NICKNAME);
		nickname = nicknameRepository.save(nickname);
		
		// when
		Nickname n = nicknameRepository.findById(honggd.getId()).get();
		System.out.println(n);
		
		// then
		assertEquals(HONGGD_NICKNAME, n.getNickname());
		
		
		
	}

}
