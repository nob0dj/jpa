package com.sh.entity.association._01.one2one._01.non.identifying.card.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "membership_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Card {
    @Id
    @Column(name = "card_no")
    private String cardNumber;
    /**
     * <pre>
     * Card to User 1:1 비식별 단방향 참조
     * - User는 null일 수 있다.
     * </pre>
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email") // fk컬럼명
    private User owner;
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    private boolean enabled;
}
