package com.sh.entity.association._02.many2one._01.review.restaurant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
