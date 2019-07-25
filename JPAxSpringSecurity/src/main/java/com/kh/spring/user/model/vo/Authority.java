package com.kh.spring.user.model.vo;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

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
@ToString(of= {"authName"}, callSuper=true)
public class Authority extends AbstractPersistable<Long> {
	
	
	@NonNull
	private String authName;
}
