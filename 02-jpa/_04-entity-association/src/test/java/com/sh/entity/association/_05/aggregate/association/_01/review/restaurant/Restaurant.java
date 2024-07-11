package com.sh.entity.association._05.aggregate.association._01.review.restaurant;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @EmbeddedId 에서는 @GeneratedValue를 사용할 수 없다.
 * 또한 RestaurantId#id에 @Id를 사용하면, 다른 애그리거트-엔티티에서 참조할 수 없다.
 * @GeneratedValue 사용하지 않고, String 등으로 사용자가 직접 @Id 필드값을 주입해야 한다.
 *
 * @Embeddable XXXId를 사용하는 것은 명확함을 주는 대신 복잡도가 많이 증가한다.
 */
@Entity(name = "Restaurant00")
@Table(name = "restaurant_00")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Restaurant {
    @EmbeddedId
    private RestaurantId id;
    private String name;
}
