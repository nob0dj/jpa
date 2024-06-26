package com.sh.app.user.repository;

import com.sh.app.user.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * mock객체 생성하기
 * 1. @ExtendWith(MockitoExtension.class) + @Mock
 * 2. Mockito.mock(clazz)
 */
@ExtendWith(MockitoExtension.class)
public class UserRepositoryBDDMockitoTest {
    /**
     * @InjectMocks
     * - @Mock이나 @Spy 객체를 자신의 멤버와 일치하면 주입
     */
    @InjectMocks
    UserRepository userRepository;
    @Mock
    Session session;


    /**
     * @InjectMocks를 사용하는 경우는 아래 코드를 작성하지 않는다.
     */
    @BeforeEach
    void setUp() {
//        this.userRepository = new UserRepository();
//        this.userRepository.setSession(this.session);
    }

    /**
     * @throws Exception
     */
    @DisplayName("회원등록")
    @Test
    public void test1() throws Exception {
        // given
        User user = User.builder()
                .username("honggd")
                .build();

        // 타겟메소드에서 값설정이 필요한 경우
        BDDMockito.given(session.save(ArgumentMatchers.any(User.class)))
            .will(new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocation) throws Throwable {
                    User _user = (User) invocation.getArgument(0);
                    _user.setId(1L);
                    _user.setCreatedAt(LocalDateTime.now());
                    return null;
                }
            });
        // when
        userRepository.save(user); // id, createdAt 발급
        System.out.println(user);
        // then
        BDDMockito.then(session).should(Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }


    @DisplayName("존재하는 회원 1명 조회")
    @Test
    public void test2() throws Exception {
        // given
        // when메소드안의 mock메소드 매개인자는 matcher타입으로 전달
        BDDMockito.given(session.get(ArgumentMatchers.eq(User.class), ArgumentMatchers.anyLong()))
                .willReturn(User.builder()
                        .id(1L)
                        .username("honggd")
                        .point(1000)
                        .createdAt(LocalDateTime.now())
                        .build());
        // when
        User user = userRepository.findById(1L);
        System.out.println(user);
        // then - verify
        BDDMockito.then(session).should(Mockito.times(1)).get(ArgumentMatchers.eq(User.class), ArgumentMatchers.anyLong());

    }

    @DisplayName("전체조회 w/ jpql(Query)")
    @Test
    public void test3() throws Exception {
        // given
        Query queryMock = Mockito.mock(Query.class);
        BDDMockito.given(session.createQuery(ArgumentMatchers.anyString(), ArgumentMatchers.eq(User.class))).willReturn(queryMock);
        BDDMockito.given(queryMock.getResultList()).willReturn(Arrays.asList(
                User.builder().id(1L).username("honggd").point(1000).createdAt(LocalDateTime.now()).build(),
                User.builder().id(2L).username("sinsa").point(1000).createdAt(LocalDateTime.now()).build(),
                User.builder().id(3L).username("leess").point(1000).createdAt(LocalDateTime.now()).build()
        ));
        // when
        List<User> users = userRepository.findAll();
        System.out.println(users);
        // then
        BDDMockito.then(session).should(Mockito.atLeastOnce()).createQuery(ArgumentMatchers.anyString(), ArgumentMatchers.eq(User.class));
        BDDMockito.then(queryMock).should(Mockito.atLeastOnce()).getResultList();
    }
}
