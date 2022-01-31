package com.kh.spring.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.room.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> 
{

}
