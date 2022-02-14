package com.kh.spring.carrier.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_carrier_telecom_company", columnNames = "telecomCompany"))
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Carrier {

	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TelecomCompany telecomCompany;
}
