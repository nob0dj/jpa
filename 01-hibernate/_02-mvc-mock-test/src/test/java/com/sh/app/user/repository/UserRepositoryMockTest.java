package com.sh.app.user.repository;

import com.sh.app.user.entity.User;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * mock객체 생성하기
 * 1. @ExtendWith(MockitoExtension.class) + @Mock
 * 2. Mockito.mock(clazz)
 */
@ExtendWith(MockitoExtension.class)
public class UserRepositoryMockTest {
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

    @DisplayName("Stubbing 전 mock객체의 상태")
    @Test
    public void test() throws Exception {
        // given
        User user = User.builder().username("honggd").build();
        // when
        Serializable id = session.save(user); // 기본값 반환, 컬렉션은 비어있는 컬렉션을 반환
        // then
        Assertions.assertThat(id).isNull();
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
            session.save(user); // void 아무 오류발생 안함.
        });
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
        Mockito.doAnswer((invocation -> {
            User _user = (User) invocation.getArgument(0);
            _user.setId(1L);
            _user.setCreatedAt(LocalDateTime.now());
            return null;
        }))
//        .when(session.save(user));
                .when(session).save(user);
        // when
        userRepository.save(user); // id, createdAt 발급
        System.out.println(user);
        // then
        Mockito.verify(session, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }


    @DisplayName("존재하는 회원 1명 조회")
    @Test
    public void test2() throws Exception {
        // given
        // when메소드안의 mock메소드 매개인자는 matcher타입으로 전달
        Mockito.when(session.get(ArgumentMatchers.eq(User.class), ArgumentMatchers.anyLong()))
                .thenReturn(User.builder()
                        .id(1L)
                        .username("honggd")
                        .point(1000)
                        .createdAt(LocalDateTime.now())
                        .build());
        // when
        User user = userRepository.findById(1L);
        System.out.println(user);
        // then - verify
        Mockito.verify(session, Mockito.times(1)).get(ArgumentMatchers.eq(User.class), ArgumentMatchers.anyLong());

    }

    @DisplayName("전체조회 w/ jpql(Query)")
    @Test
    public void test3() throws Exception {
        // given
        Query queryMock = Mockito.mock(Query.class);
        Mockito.when(session.createQuery(ArgumentMatchers.anyString(), ArgumentMatchers.eq(User.class))).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(Arrays.asList(
            User.builder().id(1L).username("honggd").point(1000).createdAt(LocalDateTime.now()).build(),
            User.builder().id(2L).username("sinsa").point(1000).createdAt(LocalDateTime.now()).build(),
            User.builder().id(3L).username("leess").point(1000).createdAt(LocalDateTime.now()).build()
        ));
        // when
        List<User> users = userRepository.findAll();
        // then
        Mockito.verify(session, Mockito.atLeastOnce()).createQuery(ArgumentMatchers.anyString(), ArgumentMatchers.eq(User.class));
        Mockito.verify(queryMock, Mockito.atLeastOnce()).getResultList();
    }
}
