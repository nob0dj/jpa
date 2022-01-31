package com.kh.spring;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.laptop.domain.Laptop;
import com.kh.spring.laptop.repository.LaptopRepository;
import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;


@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED) // 자동롤백 하지 않음 
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 db사용
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LaptopRepositoryTest {
	
	@Autowired
	LaptopRepository laptopRepository;
	
	@Autowired
	MemberRepository memberRepository;

	@Test
	@Order(1)
	void 랩탑등록() {
		Laptop laptop = new Laptop();
		assertThat("db insert전 @Id 컬럼은 null이다.", laptop.getId(), is(nullValue()));
		laptopRepository.save(laptop);
		assertThat("db insert시 @Id 컬럼값을 부여한다.", laptop.getId(), is(notNullValue()));
		
		
		List<Laptop> list = laptopRepository.findAll();		
		assertThat("조회한 list에 laptop이 포함되어 있다.", list.contains(laptop), is(true));
		assertThat("등록된 행의 수는 1이다.", list.size(), is(equalTo(1)));
	}
	

	@Test
	void 일대일_단방향1() throws Exception {
		// given
		Laptop laptop = new Laptop();
		laptop = laptopRepository.save(laptop);
		
		// when
		Member honggd = Member.builder()
							.id("honggd")
							.password("2345")
							.name("홍길동")
							.build();
		honggd.setLaptop(laptop);

		honggd = memberRepository.save(honggd);

		// then
		Member m = memberRepository.findById(honggd.getId()).get();
		assertThat("member.laptop.member == member", m, is(sameInstance(m.getLaptop().getMember())));
		
	}
	
	/**
	 * 두명의 회원이 동일한 laptop을 참조하면 예외가 던져진다.
	 * junit5에서는 @Test(expected)를 사용할 수 없고, AssertThrows(Exception.class, Executable)
	 * - Executable은 Runnable과 비슷한 junit의 @FunctionalInterface 이다.
	 * 
	 * @throws Exception
	 */
	@Test
	void 일대일_단방향2() throws Exception {
		// given
		Laptop laptop = new Laptop();
		laptopRepository.save(laptop);
		
		// when
		Member honggd = Member.builder()
							.id("honggd")
							.password("2345")
							.name("홍길동")
							.build();
		honggd.setLaptop(laptop);
		honggd = memberRepository.save(honggd);
		
		Member sinsa = Member.builder()
				.id("sinsa")
				.password("1234")
				.name("신사임당")
				.build();
		sinsa.setLaptop(laptop);
		
		assertThrows(DataIntegrityViolationException.class, () -> {	           
			memberRepository.save(sinsa);
        });
		
	}
	
	/**
	 * @Transactional(propagation = Propagation.NOT_SUPPORTED) // 자동롤백 하지 않음 
	 * 설정이 반드시 필요한 테스트
	 * 
	 * @throws Exception
	 */
	@Test
	void 일대일양방향() throws Exception {
		// given
		Laptop laptop = new Laptop();
		laptopRepository.save(laptop);
		
		Member honggd = Member.builder()
							.id("honggd")
							.password("2345")
							.name("홍길동")
							.build();
		honggd.setLaptop(laptop);
		honggd = memberRepository.save(honggd);
//		System.out.println(laptop); // Laptop [id=1, serialNumber=910374929532817, member=honggd]
//		System.out.println(honggd); // laptop객체에 member가 설정되어 있지 않다. Member(id=honggd, password=2345, name=홍길동, gender=null, birthday=null, email=null, phone=null, address=null, hobby=null, point=null, enrollDate=2022-01-24 00:00:19.961, enabled=true, team=null, laptop=Laptop [id=1, serialNumber=910374929532817, member=null])
		
		// when
		honggd = memberRepository.findById(honggd.getId()).get();
		// then
		assertEquals(honggd, honggd.getLaptop().getMember());

		// when
		laptop = laptopRepository.findById(laptop.getId()).get();
		// then
		assertEquals(laptop, laptop.getMember().getLaptop());
	}

}
