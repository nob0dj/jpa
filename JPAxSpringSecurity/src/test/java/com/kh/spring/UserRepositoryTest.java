package com.kh.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.kh.spring.user.model.repository.UserRepository;
import com.kh.spring.user.model.vo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

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
	public void saveTest() {
		User u = new User("abcde", "1234");
		u.setPassword(bcryptPasswordEncoder.encode(u.getPassword()));
		assertThat(u.isNew()).isTrue();//id값이 채워지기전
		userRepository.save(u);
		assertThat(u.isNew()).isFalse();
		
		
	}

}
