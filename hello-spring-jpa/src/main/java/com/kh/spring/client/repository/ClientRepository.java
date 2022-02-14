package com.kh.spring.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.client.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
