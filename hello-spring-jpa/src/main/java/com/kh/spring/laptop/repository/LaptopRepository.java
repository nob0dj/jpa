package com.kh.spring.laptop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.laptop.domain.Laptop;

public interface LaptopRepository extends JpaRepository<Laptop, Long>
{

}
