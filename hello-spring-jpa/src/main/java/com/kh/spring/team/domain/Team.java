package com.kh.spring.team.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kh.spring.member.domain.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uq_team_name", columnNames = "name"))
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(
        name="team_sequence", //시퀀스 제너레이터 이름
        sequenceName="seq_team_id", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1) //시퀀스객체로 부터 한번에 가져와 메모리에서 관리할 숫자크기(기본값 : 50)
@ToString(exclude = "members")
@EqualsAndHashCode(exclude = "members")
public class Team implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO) // 기본값
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
            generator="team_sequence") // class level의 @SequenceGenerator 이름 지정
	private Long id;
	
	@NonNull // lombok의 @RequiredArgsConstructor 필드로 지정 
	@Column(nullable = false) // jpa DDL 작성용
	private String name;
	
//	@JsonIgnore
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy="team")
	private List<Member> members = new ArrayList<>(); // 초기화필수
	
	public List<Member> getMembers(){
		return Collections.unmodifiableList(this.members);
	}
	
	public void addMember(Member member) {
		this.members.add(member);
		
		// 무한루프에 빠지지 않도록 함.
		if(member.getTeam() != this)
			member.setTeam(this);
	}
}
