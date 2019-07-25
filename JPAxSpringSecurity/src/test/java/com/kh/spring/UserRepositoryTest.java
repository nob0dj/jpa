package com.kh.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.kh.spring.user.model.repository.UserRepository;
import com.kh.spring.user.model.vo.Authority;
import com.kh.spring.user.model.vo.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Test
	public void test() {
		assertThat(userRepository).isNotNull();
		assertThat(bcryptPasswordEncoder).isNotNull();
	}
	
	@Test
	public void saveTest1() throws ClassNotFoundException {
		//저장
		User u = new User("abcde", "1234", true);
		Authority role_user = new Authority("USER");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(role_user);
		u.setAuthorities(authorities);
		u.setPassword(bcryptPasswordEncoder.encode(u.getPassword()));
		
		assertThat(u.isNew()).isTrue();//id값을 jpa로부터 부여받기 전
		userRepository.save(u);
		assertThat(u.isNew()).isFalse();//id값을 jpa로부터 부여받은 후
		
		//조회
		Optional<User> uMayBe = userRepository.findById(u.getId());
		
		assertThat(uMayBe.get(), is(not(sameInstance(u))));
		assertThat(uMayBe.get().getId(), is(equalTo(u.getId())));
		assertThat(uMayBe.get(), is(equalTo(u)));
		
	}
	
	@Test
	public void saveTest2() throws Exception {
		//등록
		for(int i=1; i<=10; i++) {
			List<Authority> authorities = new ArrayList<>();
			switch(new Random().nextInt(3)+1) {
			case 1: authorities.add(new Authority("DEV"));
			case 2: authorities.add(new Authority("MANAGER"));
			case 3: authorities.add(new Authority("USER"));
			}
			
			User u = User.builder()
						 .userId("user"+i)
						 .password(bcryptPasswordEncoder.encode("pwd"+i))
						 .email(new Random().nextBoolean()?"user"+i+"@naver.com":null)
						 .authorities(authorities)
						 .eanabled(true)
						 .build();
			userRepository.save(u);
		}
		
		//조회
		List<User> list = userRepository.findAll();
		list.forEach(u -> {
			logger.info("{}",u);
		});
	}
	

}
