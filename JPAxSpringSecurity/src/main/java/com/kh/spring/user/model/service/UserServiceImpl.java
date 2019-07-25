package com.kh.spring.user.model.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.spring.user.model.repository.UserRepository;
import com.kh.spring.user.model.vo.Authority;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepository.findByUserId(userId)
								  .filter(u -> u!=null)
								  .map(u -> new User(u.getUserId(), u.getPassword(), makeGrantedAuthority(u.getAuthorities())))
								  .get();
		//user: org.springframework.security.core.userdetails.User@584f463: Username: abcde; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: USER
		return user;
	}

	/**
	 * spring-security 인증을 위해 필요한 타입은 UserDetials구현체이다.
	 * 직접 UserDetails인터페이스를 구현하거나,
	 * org.springframework.security.core.userdetails.User를 상속해서 사용할 수 있다.
	 * 
	 * 필수적으로 <code>Collection<GrantedAuthority> getAuthorities()</code>를 구현해야한다.
	 * jpa연동시에 이를 vo에서 직접 저 메소드를 구현하면, 해당타입이 @Entity 등록이 되어있지 않아 번거롭다.
	 * 그래서 별도로 해당타입을 리턴할 수 있는 메소드 makeGrantedAuthority를 작성한다.
	 * 
	 * @param authorities
	 * @return
	 */
	private Collection<? extends GrantedAuthority> makeGrantedAuthority(List<Authority> authorities) {
		List<GrantedAuthority> list = new ArrayList<>();
		authorities.forEach(a -> {
			list.add(new SimpleGrantedAuthority(a.getAuthName()));
		});
		
		return list;
	}
	
	
	
}
