package com.kh.spring.user.model.vo;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of= {"authority"}, callSuper=true)
public class Authority extends AbstractPersistable<Long> 
	/*[[2:직접구현체만들기]]*/ implements GrantedAuthority{
	
	
	@NonNull
	private String authority;

	@Override
	public String getAuthority() {
		return authority;
	}
}
