package com.kh.spring.room.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.kh.spring.member.domain.Member;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "member")
public class Room implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
//	public void setMember(Member member) {
//		if(this.member != null) {
//			this.member.setRoom(null);
//		}
//		
//		this.member = member;
//		
//		if(member != null && member.getRoom() != this) {
//			member.setRoom(this);
//		}
//	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", member=" + (member != null ? member.getId() : null) + "]";
	}
	
	
}
