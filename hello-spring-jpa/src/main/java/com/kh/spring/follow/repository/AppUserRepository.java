package com.kh.spring.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.follow.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

}
