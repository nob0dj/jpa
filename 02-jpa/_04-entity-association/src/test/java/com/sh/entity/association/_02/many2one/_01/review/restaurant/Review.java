package com.sh.entity.association._02.many2one._01.review.restaurant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private int grade;
    private String comment;
}
