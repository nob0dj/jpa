package com.kh.spring.appmember.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_app_member_detail_nickname", columnNames = "nickname"))
@Data
@NoArgsConstructor
@ToString(exclude = "appMember")
@EqualsAndHashCode(exclude = "appMember")
public class AppMemberDetail {

	@Id
	private Long memberId; // AppMember#id
	
	@MapsId // 같은 클래스 AppMemberOption#memberId 매핑
	@OneToOne
	@JoinColumn(name = "member_id")
	@Access(AccessType.PROPERTY)
	private AppMember appMember;
	
	/**
	 * 양방향 setter 편의메소드
	 * @param appMember
	 */
	public void setAppMember(AppMember appMember) {
		this.appMember = appMember;
		if(appMember != null && appMember.getAppMemberDetail() != this)
			appMember.setAppMemberDetail(this);
	}
	
	@Column(nullable = false)
	private String nickname;
	
}
