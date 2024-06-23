package com.sh.entity.association._01.one2one._01.non.identifying.card.user.repository;

import com.sh.entity.association._01.one2one._01.non.identifying.card.user.entity.Card;
import com.sh.entity.association._01.one2one._01.non.identifying.card.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;
    String email = "honggd@gmail.com";
    @BeforeEach
    void setUp() {
        // true인 경우에만 test를 진행한다. (@BeforeEach에서 assumeTrue결과가 거짓이면 @Test 메소드를 실행하지 않는다.)
        assumeTrue(userRepository.findById(email).isEmpty());
        userRepository.saveAndFlush(new User(email, "홍길동", null));
    }

    @DisplayName("Card - User 등록")
    @Test
    public void test() throws Exception {
        // given
        User user = userRepository.findById("honggd@gmail.com").get();
        Card card = new Card("1111-2222-3333-4444", user, LocalDate.of(2026, 11, 1), true);
        // when
        card = cardRepository.saveAndFlush(card);
        System.out.println(card);
        // then
        Card card2 = cardRepository.findById(card.getCardNumber()).get();
        assertThat(card2).isSameAs(card);
        assertThat(card2.getOwner()).isNotNull();
    }
    @DisplayName("Card - User(null) 등록")
    @Test
    public void test2() throws Exception {
        // given
        Card card = new Card("1111-2222-3333-4444", null, LocalDate.of(2026, 11, 1), true);
        // when
        card = cardRepository.saveAndFlush(card);
        System.out.println(card);
        // then
        Card card2 = cardRepository.findById(card.getCardNumber()).get();
        assertThat(card2).isSameAs(card);
        assertThat(card2.getOwner()).isNull();
    }
}