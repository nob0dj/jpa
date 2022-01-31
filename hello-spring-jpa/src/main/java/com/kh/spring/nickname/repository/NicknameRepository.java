package com.kh.spring.nickname.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.nickname.domain.Nickname;

public interface NicknameRepository extends JpaRepository<Nickname, String> {

}
