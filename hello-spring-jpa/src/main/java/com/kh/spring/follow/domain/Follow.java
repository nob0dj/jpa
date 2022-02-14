package com.kh.spring.follow.domain;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Follow {

	
	// @MapsId 추가후 
	// id필드가 null이라 발생하는 [JpaSystemException: Could not set field value [followee] value by reflection] 예방하고자 객체생성한다.
	@EmbeddedId
	private FollowId id = new FollowId(); 
	
	@NonNull
	@MapsId("followee") // FollowId#followee
	@ManyToOne
	@JoinColumn(name = "followee")
	private AppUser followee;
	
	@NonNull
	@MapsId("follower") // FollowId#follower 
	@ManyToOne
	@JoinColumn(name = "follower")
	private AppUser follower;
	
	@ColumnDefault("systimestamp") // for DDL only 
	private Timestamp followDate;
	
	
	@PrePersist
	public void prePersist() {
		this.followDate = this.followDate == null ? new Timestamp(System.currentTimeMillis()) : followDate;
	}

}
