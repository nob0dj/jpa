package com.sh.entity.mapping._02.embeddable._01.single.table._02.user.repository;

import com.sh.entity.mapping._02.embeddable._01.single.table._02.user.entity.Address;
import com.sh.entity.mapping._02.embeddable._01.single.table._02.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @DisplayName("회원 등록 및 조회")
    @Test
    public void test() throws Exception {
        // given
        Address homeAddress = new Address("서울시 서초구", "방배동 123", "03333");
        Address workAddress = new Address("서울시 강남구", "역삼동 123", "01234");
        User user = new User("honggd", homeAddress, workAddress);
        // when
        user = userRepository.save(user);
        // then
        User user2 = userRepository.findById(user.getId()).get();
        System.out.println(user2);
    }
}