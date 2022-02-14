package com.kh.spring.appmember.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.appmember.domain.AppMemberDetail;

public interface AppMemberDetailRepository extends JpaRepository<AppMemberDetail, Long> {

}
