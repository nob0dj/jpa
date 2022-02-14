package com.kh.spring.member.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.kh.spring.carrier.domain.Carrier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

	@ManyToOne(cascade = CascadeType.ALL)
	private Carrier carrier;
	
	private String phoneNumber;
}
