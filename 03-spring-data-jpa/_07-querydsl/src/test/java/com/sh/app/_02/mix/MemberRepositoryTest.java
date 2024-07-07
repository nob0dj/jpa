package com.sh.app._02.mix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("남자 회원 조회")
    void test1() {
        // given
        // when
        List<Member> members = memberRepository.findByGender(Gender.MALE);
        System.out.println(members);
        // then
        assertThat(members)
                .allMatch((member) -> member.getGender() == Gender.MALE);
    }

}