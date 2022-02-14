package com.kh.spring.appmember.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppMember {

	@Id
	@GeneratedValue
	private Long id;			// 사용자 식별코드 PK
	
	@Column(nullable = false)
	private String username; 	// 사용자아이디
	
	@OneToOne(mappedBy = "appMember", cascade = CascadeType.ALL)
	private AppMemberDetail appMemberDetail;
}
