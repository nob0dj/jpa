package com.kh.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.kh.spring.appmember.domain.AppMember;
import com.kh.spring.appmember.domain.AppMemberDetail;
import com.kh.spring.appmember.repository.AppMemberDetailRepository;
import com.kh.spring.appmember.repository.AppMemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppMemberRepositoryTest {
	
	@Autowired
	AppMemberRepository appMemberRepository;
	
	@Autowired
	AppMemberDetailRepository appMemberDetailRepository;

	@DisplayName("회원별칭저장@AppMemberDetail")
	@Rollback(false)
	@Test
	void test1() {
		// given
		AppMember honggd = AppMember.builder()
							.username("honggd")
							.build();
		honggd = appMemberRepository.save(honggd);
		
		final String HONGGD_NICKNAME = "홍길동구리";
		AppMemberDetail appMemberDetail = new AppMemberDetail(); 
		appMemberDetail.setAppMember(honggd); // 이때 honggd#appMemberDetail = null이므로 편의메소드 AppMemberDetail#setAppMember를 이용해 양방향으로 저장한다.
		appMemberDetail.setNickname(HONGGD_NICKNAME);
		
		appMemberDetail = appMemberDetailRepository.save(appMemberDetail); // 외래키의 주인에서 저장

		
		// when : AppMemberOption -> AppMember
		AppMemberDetail _appMemberDetail = appMemberDetailRepository.findById(honggd.getId()).get();
		System.out.println(_appMemberDetail); // AppMemberDetail(memberId=1, nickname=홍길동구리)
		
		// then
		assertEquals(honggd, _appMemberDetail.getAppMember());
		assertEquals(HONGGD_NICKNAME, _appMemberDetail.getNickname());
		
		// when : AppMember -> AppMemberOption
		AppMember appMember = appMemberRepository.findById(honggd.getId()).get();
		System.out.println(appMember); // AppMember(id=1, username=honggd, appMemberDetail=AppMemberDetail(memberId=1, nickname=홍길동구리))
		
		// then
		assertEquals(honggd, appMember);
		assertEquals(HONGGD_NICKNAME, appMember.getAppMemberDetail().getNickname());
		
	}
	
	/**
	 * AppMember#appMemberDetail 영속성전이를 이용해 저장할 수 있다.
	 */
	@DisplayName("회원별칭저장@AppMember")
	@Rollback(false)
	@Test
	void test2() {
		// given
		AppMember sinsa = AppMember.builder()
							.username("sinsa")
							.build();
		
		final String SINSA_NICKNAME = "재텍신사";
		AppMemberDetail appMemberDetail = new AppMemberDetail(); 
		appMemberDetail.setAppMember(sinsa); // 이때 honggd#appMemberDetail = null이므로 편의메소드 AppMemberDetail#setAppMember를 이용해 양방향으로 저장한다.
		appMemberDetail.setNickname(SINSA_NICKNAME);
		
		sinsa = appMemberRepository.save(sinsa); // 외래키의 주인이 아니지만, @OneToOne(cascade=CascadeTyp.ALL)을 통해 영속성 전이

		
		// when : AppMemberOption -> AppMember
		AppMemberDetail _appMemberDetail = appMemberDetailRepository.findById(sinsa.getId()).get();
		System.out.println(_appMemberDetail); // AppMemberDetail(memberId=2, nickname=재텍신사)
		
		// then
		assertEquals(sinsa, _appMemberDetail.getAppMember());
		assertEquals(SINSA_NICKNAME, _appMemberDetail.getNickname());
		
		// when : AppMember -> AppMemberOption
		AppMember appMember = appMemberRepository.findById(sinsa.getId()).get();
		System.out.println(appMember); // AppMember(id=2, username=sinsa, appMemberDetail=AppMemberDetail(memberId=2, nickname=재텍신사))
		
		// then
		assertEquals(sinsa, appMember);
		assertEquals(SINSA_NICKNAME, appMember.getAppMemberDetail().getNickname());
		
	}

}
