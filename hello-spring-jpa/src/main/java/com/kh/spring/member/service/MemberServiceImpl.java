package com.kh.spring.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Member save(Member member) {
		return memberRepository.save(member);
	}

	@Override
	public Optional<Member> findById(String id) {
		return memberRepository.findById(id);
	}

	@Override
	public void deleteById(String id) {
		memberRepository.deleteById(id);
	}
	
	

	
}
