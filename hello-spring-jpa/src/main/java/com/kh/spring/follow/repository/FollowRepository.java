package com.kh.spring.follow.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.follow.domain.Follow;
import com.kh.spring.follow.domain.FollowId;
import com.kh.spring.member.domain.Member;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

	List<Follow> findByIdFollowee(String followee);
	List<Follow> findByFollowee(Member followee);
	
	List<Follow> findByIdFollower(String follower);
	List<Follow> findByFollower(Member follower);

}
