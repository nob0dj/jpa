package com.kh.spring.nickname.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.kh.spring.member.domain.Member;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_nickname", columnNames = "nickname"))
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Nickname {

	@Id
	private String memberId; // Member#id
	
	@NonNull
	@MapsId // Nickname#memberId 매핑
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@NonNull
	@Column(nullable = false)
	private String nickname;
	
}
