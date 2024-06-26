package com.sh.app.user.service;

import com.sh.app.user.entity.User;
import com.sh.app.user.repository.UserRepository;
import org.hibernate.query.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {
    /**
     * 인터페이스 대신 구현클래스를 작성해야 한다.
     */
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

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
            _user.setPoint(1000);
            _user.setCreatedAt(LocalDateTime.now());
            return null;
        }))
//        .when(userRepository.save(user));
        .when(userRepository).save(user);
        // when
        userService.save(user); // id, createdAt 발급
        System.out.println(user);
        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }
    @DisplayName("회원등록실패")
    @Test
    public void test1_2() throws Exception {
        // given
        User user = User.builder()
                .username("honggd")
                .build();
        Mockito.doThrow(new RuntimeException("username 중복 오류!!!"))
                .when(userRepository)
                .save(user);
        // when
        AssertionsForClassTypes.assertThatThrownBy(() -> {
            userService.save(user);
        })
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("중복");

        // then - 예외 관련해서는 별도로 검증할 것이 없다.
        Mockito.verify(userRepository, Mockito.atLeastOnce()).save(ArgumentMatchers.any(User.class));
    }


    @DisplayName("존재하는 회원 1명 조회")
    @Test
    public void test2() throws Exception {
        // given
        // when메소드안의 mock메소드 매개인자는 matcher타입으로 전달
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(User.builder()
                        .id(1L)
                        .username("honggd")
                        .point(1000)
                        .createdAt(LocalDateTime.now())
                        .build());
        // when
        User user = userService.findById(1L);
        System.out.println(user);
        // then - verify
        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());

    }

    @DisplayName("전체조회 w/ jpql(Query)")
    @Test
    public void test3() throws Exception {
        // given
        Query queryMock = Mockito.mock(Query.class);
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(
                User.builder().id(1L).username("honggd").point(1000).createdAt(LocalDateTime.now()).build(),
                User.builder().id(2L).username("sinsa").point(1000).createdAt(LocalDateTime.now()).build(),
                User.builder().id(3L).username("leess").point(1000).createdAt(LocalDateTime.now()).build()
        ));
        // when
        List<User> users = userRepository.findAll();
        // then
        Mockito.verify(userRepository, Mockito.atLeastOnce()).findAll();
    }

}
