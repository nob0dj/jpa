package com.kh.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;


//insecure 테스트용
//public class SecurityConfig {}

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(WebSecurity web) throws Exception {
		//권한이 필요없는 url설정
		web.ignoring().antMatchers("/images/**", "/favicon.ico");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/index").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")//loginPage명시
				.permitAll()				
				.and()
			.logout()
				.permitAll();
	}

	@Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
				                .username("user")
				                .password("1234")
				                .roles("USER")
				                .build();
        
        UserDetails admin = User.withDefaultPasswordEncoder()
			                   .username("admin")
			                   .password("1234")
			                   .roles("USER", "ADMIN")
			                   .build();
           
        //org.springframework.security.provisioning.InMemoryUserDetailsManager.InMemoryUserDetailsManager(UserDetails... users)
        return new InMemoryUserDetailsManager(user, admin);//가변매개인자 UserDetials
    }
	
	
	/**
	 * html파일에서 sec - namespace사용을 위한 빈추가
	 * xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5"
	 * 
	 * @return
	 */
	@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
	
	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
