package com.sh.entity.association._02.many2one._01.review.restaurant.repository;

import com.sh.entity.association._02.many2one._01.review.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
