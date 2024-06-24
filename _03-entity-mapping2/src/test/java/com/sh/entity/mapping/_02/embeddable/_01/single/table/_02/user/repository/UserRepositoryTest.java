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

    /*
        create table user (
            addr1 varchar(255),
            addr2 varchar(255),
            id varchar(255) not null,
            work_addr1 varchar(255),
            work_addr2 varchar(255),
            work_zipcode varchar(255),
            zipcode varchar(255),
            primary key (id)
        ) engine=InnoDB
     */

    @DisplayName("회원 등록 및 조회")
    @Test
    public void test() throws Exception {
        // given
        Address homeAddress = new Address("서울시 서초구", "방배동 123", "03333");
        Address workAddress = new Address("서울시 강남구", "역삼동 123", "01234");
        User user = new User("honggd", homeAddress, workAddress);
        // when
        user = userRepository.saveAndFlush(user);
        // then
        User user2 = userRepository.findById(user.getId()).get(); // 영속성 컨텍스트에 entity객체가 있으므로 select쿼리 실행 안함
        System.out.println(user2);
    }
}