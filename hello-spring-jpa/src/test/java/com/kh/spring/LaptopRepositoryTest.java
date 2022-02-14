package com.kh.spring;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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

import com.kh.spring.client.domain.Client;
import com.kh.spring.client.repository.ClientRepository;
import com.kh.spring.laptop.domain.Laptop;
import com.kh.spring.laptop.repository.LaptopRepository;


@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED) // 자동롤백 하지 않음 
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 db사용
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LaptopRepositoryTest {
	
	@Autowired
	LaptopRepository laptopRepository;
	
	@Autowired
	ClientRepository clientRepository;

	@Disabled
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
	

	@Disabled
	@Test
	void 일대일_단방향1() throws Exception {
		// given
		Laptop laptop = new Laptop();
		laptop = laptopRepository.save(laptop);
		
		// when
		Client honggd = Client.builder()
							.name("홍길동")
							.build();
		honggd.setLaptop(laptop);

		honggd = clientRepository.save(honggd);

		// then
		Client m = clientRepository.findById(honggd.getId()).get();
		assertThat("client.laptop.client == client", m, is(sameInstance(m.getLaptop().getClient())));
		
	}
	
	/**
	 * 두명의 회원이 동일한 laptop을 참조하면 예외가 던져진다.
	 * junit5에서는 @Test(expected)를 사용할 수 없고, AssertThrows(Exception.class, Executable)
	 * - Executable은 Runnable과 비슷한 junit의 @FunctionalInterface 이다.
	 * 
	 * @Transactional(propagation = Propagation.NOT_SUPPORTED) 설정이 반드시 필요하다. 
	 * 
	 * @throws Exception
	 */
	@Disabled
	@Test
	void 일대일_단방향2() throws Exception {
		// given
		Laptop laptop = new Laptop();
		laptopRepository.save(laptop);
		
		// when
		Client honggd = Client.builder()
							.name("홍길동")
							.build();
		
		System.out.println("laptop = " + laptop);
		System.out.println("honggd = " + honggd);
		honggd.setLaptop(laptop);
		honggd = clientRepository.save(honggd);
		
		Client sinsa = Client.builder()
				.name("신사임당")
				.build();
		sinsa.setLaptop(laptop);
		
		assertThrows(DataIntegrityViolationException.class, () -> {	           
			clientRepository.save(sinsa);
        });
		
	}
	
	/**
	 * @Transactional(propagation = Propagation.NOT_SUPPORTED) // 자동롤백 하지 않음 
	 * 설정이 반드시 필요한 테스트
	 * 
	 * @throws Exception
	 */
	@Disabled
	@DisplayName("Client에서 Laptop추가하기")
	@Test
	void 일대일양방향1() throws Exception {
		// given
		Laptop laptop = new Laptop();
		laptop = laptopRepository.save(laptop);
		
		Client honggd = Client.builder()
							.name("홍길동")
							.build();
		honggd.setLaptop(laptop);
		honggd = clientRepository.save(honggd);
//		System.out.println(laptop); // Laptop [id=1, serialNumber=910374929532817, client=honggd]
//		System.out.println(honggd); // laptop객체에 client가 설정되어 있지 않다. 
		
		// when
		honggd = clientRepository.findById(honggd.getId()).get();
		// then
		assertEquals(honggd, honggd.getLaptop().getClient());

		// when
		laptop = laptopRepository.findById(laptop.getId()).get();
		// then
		assertEquals(laptop, laptop.getClient().getLaptop());
	}

	@DisplayName("Laptop에서 Client추가하기")
//	@Disabled
	@Test
	void 일대일양방향2() throws Exception {
		// given
		Client honggd = Client.builder()
				.name("홍길동")
				.build();
		honggd = clientRepository.save(honggd);
		
		// when
		Laptop laptop = new Laptop();
		laptop.setClient(honggd);
		laptop = laptopRepository.save(laptop); // 단 자바객체관계만 형성된 것이고, client테이블에 대한 update문을 실행하지 않는다. (외래키주인이 아니므로)
		
		// then
		assertNotNull(honggd.getLaptop());
		assertEquals(laptop, honggd.getLaptop());
		
		// when
		honggd = clientRepository.save(honggd);
		Client client = clientRepository.findById(honggd.getId()).get();
		System.out.println(client);
		// then 
		assertEquals(laptop, client.getLaptop());
	}
}
