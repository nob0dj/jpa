package com.kh.spring.appmember.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.appmember.domain.AppMember;

public interface AppMemberRepository extends JpaRepository<AppMember, Long> {

}
