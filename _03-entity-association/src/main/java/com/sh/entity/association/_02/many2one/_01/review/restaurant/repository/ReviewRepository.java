package com.sh.entity.association._02.many2one._01.review.restaurant.repository;

import com.sh.entity.association._02.many2one._01.review.restaurant.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
