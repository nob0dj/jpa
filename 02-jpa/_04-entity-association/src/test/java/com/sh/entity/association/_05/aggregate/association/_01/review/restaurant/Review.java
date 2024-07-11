package com.sh.entity.association._05.aggregate.association._01.review.restaurant;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Review00")
@Table(name = "restaurant_review_00")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @AttributeOverride(name = "id", column = @Column(name = "restaurant_id"))
    private RestaurantId restaurantId;
    private int grade;
    private String comment;
}
