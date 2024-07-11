package com.sh.entity.association._05.aggregate.association._01.review.restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Random;

@Embeddable
@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantId implements Serializable {
    @Column(name = "id")
    private String id;

    public static RestaurantId generate() {
        String id = new Random().ints('a', 'z' + 1)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println("id generated : " + id);
        return new RestaurantId(id);
    }
}
