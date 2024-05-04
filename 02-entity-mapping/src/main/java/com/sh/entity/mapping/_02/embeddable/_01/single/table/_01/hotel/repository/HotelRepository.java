package com.sh.entity.mapping._02.embeddable._01.single.table._01.hotel.repository;

import com.sh.entity.mapping._02.embeddable._01.single.table._01.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
