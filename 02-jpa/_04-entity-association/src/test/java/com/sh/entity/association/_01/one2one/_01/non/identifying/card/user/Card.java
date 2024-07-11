package com.sh.entity.association._01.one2one._01.non.identifying.card.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.YearMonth;

@Entity
@Table(name = "membership_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
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
    @OneToOne
    @JoinColumn(name = "user_email") // fk컬럼명
    private User owner;
    @Column(name = "expiry_date")
    private YearMonth expiryDate;
    private boolean enabled;
}
