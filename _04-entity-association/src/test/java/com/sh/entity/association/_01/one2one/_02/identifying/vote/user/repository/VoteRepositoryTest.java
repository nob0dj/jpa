package com.sh.entity.association._01.one2one._02.identifying.vote.user.repository;

import com.sh.entity.association._01.one2one._02.identifying.vote.user.entity.User2;
import com.sh.entity.association._01.one2one._02.identifying.vote.user.entity.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class VoteRepositoryTest {
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    User2Repository user2Repository;
    String email = "honggd@gmail.com";
    @BeforeEach
    void setUp() {
        assumeTrue(user2Repository.findById(email).isEmpty());
        user2Repository.saveAndFlush(new User2(email, "홍길동", null));
    }

    /**
     * <pre>
     * 테스트 환경에서 Vote#user:User에 @OneToOne(cascade = CascadeType.ALL)이 필요하다.
     * 없다면, vote객체 저장시 null identifier (com.sh.entity.association._01.one2one._02.identifying.user.vote.entity.Vote) 오류난다.
     *
     * TODO: 실제 실행환경에서는 다를까?
     *
     * </pre>
     * @throws Exception
     */
    @DisplayName("Vote-User 등록")
    @Test
    public void test() throws Exception {
        // given
        User2 user = user2Repository.findById(email).get();
        System.out.println(user);
        Vote vote = new Vote(user, "허경영");
        System.out.println(vote);
        // when
        vote = voteRepository.saveAndFlush(vote);
        // then
        Vote vote2 = voteRepository.findById(email).get();
        assertThat(vote2.getUser()).isNotNull().isSameAs(user);
    }
    @DisplayName("Vote-User(null) 등록하면 오류가 발생한다.")
    @Test
    public void test2() throws Exception {
        // given
        User2 user = null;
        // when
        assertThatThrownBy(() -> {
            Vote vote = new Vote(user, "허경영");
        }).isInstanceOf(NullPointerException.class);
        // then
    }
}