package com.kh.spring.member.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kh.spring.laptop.domain.Laptop;
import com.kh.spring.room.domain.Room;
import com.kh.spring.team.domain.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member", uniqueConstraints = {
		@UniqueConstraint(name = "uq_member_email", columnNames = {"email"}),
		@UniqueConstraint(name = "uq_member_laptop_id", columnNames = {"laptop_id"})
		})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//@DynamicInsert // null인 컬럼은 insert시 제외한다.
//@DynamicUpdate
public class Member implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length = 20)
	private String id;
	
	@Column(nullable = false, columnDefinition = "char(300)")
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Gender gender; // Gender.M, Gender.F, null
	
	@Temporal(TemporalType.DATE) // 날짜만 관리
	private Date birthday;
	
	private String email;
	
	
	/**
	 * @Column(nullable=true) 기본값이다.
	 * 
	 * 기본타입인 경우에는 @Column(nullable=false)로 처리하는 것이 안전하다
	 * 
	 * @Column 어노테이션을 사용하지 않는 경우 아래와 같이 처리된다.
	 */
//	private int point; // not null로 생성
	private Integer point; // nullable로 생성
	
	// 년월일 시분초까지 제어할 경우 아래 설정
	@Temporal(TemporalType.TIMESTAMP) // (기본값) @Temporal은 j.u.Date, j.u.Calendar에 대해서만 설정가능
	@ColumnDefault("sysdate") // for DDL only 
	private Date enrollDate;
	
	@ColumnDefault("1") // for DDL only
	private Boolean enabled;
	
	/**
	 * 한 회원은 하나의 그룹에 속할 수 있다.
	 * 회원 - 그룹 : N - 1
	 * 그룹 - 회원 : 1 - N
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	private Team team;
	
	public void setTeam(Team team) {
		
		// 기존 팀에서 제거하기
		if(this.team != null)
			this.team.getMembers().remove(this);
		
		this.team = team;
		if(team != null)
			if(!team.getMembers().contains(this))
				team.getMembers().add(this);
		
//		Optional.ofNullable(team)
//			.ifPresent(t -> {
//				// 무한루프에 빠지지 않도록 확인
//				if(!t.getMembers().contains(this))
//					t.getMembers().add(this);
//			});

	}
	
	/**
	 * insert하기전 실행.
	 * 
	 * @DynamicInsert 로 실행시 해당필드가 제외되므로 기본값으로 insert되지만, 
	 * 영속성 컨텍스트 1차캐시의 entity는 해당필드가 null인 상태로 관리된다.
	 */
	@PrePersist
	public void prePersist() {
        enrollDate = enrollDate == null ? new Timestamp(System.currentTimeMillis()) : enrollDate; 
        enabled = enabled == null ? true : enabled; 
    }
	
	/**
	 * 일대일 - 주테이블에 외래키
	 */
	@OneToOne
	@JoinColumn(name = "laptop_id")
	private Laptop laptop;
	
	public void setLaptop(Laptop laptop) {
		
		// 기존 laptop에서 member제거
		if(this.laptop != null && this.laptop != laptop)
			this.laptop.setMember(null);
		
		this.laptop = laptop;
		if(laptop != null && laptop.getMember() != this) {
			laptop.setMember(this);
		}
	}
	
	/**
	 * 일대일 - 대상테이블에 외래키 
	 * 양방향만 가능
	 */
	@OneToOne(mappedBy="member")
	private Room room;
	
	//EmmbeddedType
	@Embedded
	private Phone phone;
	
	@Embedded
	private Address address;
	
	
	/**
	 * columnDefinition 지정안할 경우 hobby binary(256) 생성
	 * varchar2(256) 지정해도 text encoding된 값이 저장된다.
	 * 
	 */
//	@Column(columnDefinition = "varchar2(256)") // columnDefinition for DDL only 
//	private String[] hobby;
	
	@ElementCollection(targetClass=String.class)
	private List<String> hobby;
		
}
/*
	 create table tb_member (
 	    id varchar(20) not null,
        address varchar(255),
        birthday timestamp,
        email varchar(255),
        enabled boolean default 1 not null,
        enroll_date timestamp default systimestamp,
        gender varchar(255),
        -- hobby binary(255), -- @Column(columnDefinition = "varchar2(256)") 직접 지정
        hobby varchar2(256),
        name varchar(255),
        password varchar(20) not null,
        phone varchar(255),
        point integer,
        laptop_id bigint,  -- @OneToOne
        team_id bigint,
        primary key (id)
    )
    
    alter table member 
       add constraint uq_member_email unique (email)
    
    insert 
    into
        tb_member
        (address, birthday, email, enabled, enroll_date, gender, hobby, name, password, phone, id) 
    values
        (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
 */
