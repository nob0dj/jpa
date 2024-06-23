package com.sh.entity.association._02.many2one._01.review.restaurant.repository;

import com.sh.entity.association._02.many2one._01.review.restaurant.entity.Restaurant;
import com.sh.entity.association._02.many2one._01.review.restaurant.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @DisplayName("Review 등록 및 조회")
    @Test
    public void test() throws Exception {
        // given
        Restaurant restaurant = new Restaurant(null, "덮자/덮어/덮은거야");
        restaurantRepository.saveAndFlush(restaurant);
        Review review = new Review(null, restaurant, 5, "제 최애덮밥집입니다.");
        // when
        review = reviewRepository.saveAndFlush(review);
        // then
        assertThat(review.getId()).isNotNull().isNotZero();
        Review review2 = reviewRepository.findById(review.getId()).get();
        System.out.println(review2);
        assertThat(review2.getRestaurant()).isNotNull();
    }

    /**
     * <pre>
     * N:1(Review -> Restaurant) 반대의 경우 Restaurant -> Review 조회하기
     * - select * from review where restaurant_id = ?
     * jpql 또는 query method로 해결할 수 있다.
     * </pre>
     * @throws Exception
     */
    @DisplayName("특정 Restaurant의 Review 조회하기 - jpql 또는 query method")
    @Test
    public void test2() throws Exception {
        // given
        // when
        // then
    }

}