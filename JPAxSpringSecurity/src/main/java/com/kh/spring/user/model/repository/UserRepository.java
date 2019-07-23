package com.kh.spring.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.user.model.vo.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
