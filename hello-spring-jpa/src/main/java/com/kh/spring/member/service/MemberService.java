package com.kh.spring.member.service;

import java.util.Optional;

import com.kh.spring.member.domain.Member;

public interface MemberService {

	Member save(Member member);

	Optional<Member> findById(String id);

	void deleteById(String id);

	
}
