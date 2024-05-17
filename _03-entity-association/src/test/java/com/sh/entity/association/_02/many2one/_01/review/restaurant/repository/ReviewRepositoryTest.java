package com.sh.entity.association._02.many2one._01.review.restaurant.repository;

import com.sh.entity.association._02.many2one._01.review.restaurant.entity.Restaurant;
import com.sh.entity.association._02.many2one._01.review.restaurant.entity.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        // true인 경우에만 test를 진행한다. (@BeforeEach에서 assumeTrue결과가 거짓이면 @Test 메소드를 실행하지 않는다.)
        assumeTrue(restaurantRepository.findById(1L).isEmpty());
        Restaurant restaurant = new Restaurant(null, "덮자/덮어/덮은거야");
        restaurantRepository.saveAndFlush(restaurant);
    }

    @DisplayName("Review 등록 및 조회")
    @Test
    public void test() throws Exception {
        // given
        Restaurant restaurant = restaurantRepository.findById(1L).get();
        Review review = new Review(null, restaurant, 5, "제 최애덮밥집입니다.");
        // when
        review = reviewRepository.saveAndFlush(review);
        // then
        assertThat(review.getId()).isNotNull().isNotZero();
        Review review2 = reviewRepository.findById(review.getId()).get();
        System.out.println(review2);
        assertThat(review2.getRestaurant()).isNotNull();
    }

}