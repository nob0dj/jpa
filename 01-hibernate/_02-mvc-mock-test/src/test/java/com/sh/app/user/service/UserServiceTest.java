package com.sh.app.user.service;

import com.sh.app.user.entity.User;
import com.sh.app.user.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    static SessionFactory sessionFactory;
    UserRepository userRepository;
    UserService userService;

    @BeforeAll
    public static void beforeAll() {
        Configuration config = new Configuration();
        // resourceName이 기본값인 경우 생략가능
        String resourceName = "hibernate.cfg.xml"; // 기본값
        config.configure(resourceName);
        sessionFactory = config.buildSessionFactory();
    }

    @BeforeEach
    void setUp() {
        this.userRepository = new UserRepository();
        this.userService = new UserServiceProxy(new UserServiceImpl(userRepository), sessionFactory);
    }

    @DisplayName("사용자 등록 - 트랜잭션 처리")
    @Test
    public void test1() throws Exception {
        // given
        User user = User.builder()
                .username("honggd")
                .build();
        System.out.println(user);
        // when
        userService.save(user); // id 발급
        System.out.println(user);
        // then
        Assertions.assertThat(user.getId()).isNotNull().isNotZero();
    }

    @DisplayName("존재하는 회원 1명 조회")
    @Test
    public void test2() throws Exception {
        User user = User.builder()
                .username("sinsa")
                .build();
        System.out.println(user);

        // when
        userService.save(user); // id 발급

        // then
        Assertions.assertThat(user.getId()).isNotNull().isNotZero();
        User user2 = userService.findById(user.getId());
        System.out.println(user2);
        Assertions.assertThat(user2)
                .isNotNull()
                .satisfies((_user) -> {
                    Assertions.assertThat(_user.getId()).isEqualTo(user.getId());
                    Assertions.assertThat(_user.getUsername()).isEqualTo(user.getUsername());
                    Assertions.assertThat(_user.getCreatedAt()).isEqualTo(user.getCreatedAt());
                });
    }

    @DisplayName("존재하지 않는 회원 1명 조회")
    @Test
    public void test2_2() throws Exception {
        // given
        Long id = 99999999999999L;
        // when
        User user = userService.findById(id);
        // then
        Assertions.assertThat(user).isNull();
    }

    @DisplayName("전체조회 w/ jpql(Query)")
    @Test
    public void test3() throws Exception {
        // given
        List<User> givenUsers = Arrays.asList(
                User.builder().username("honggd").build(),
                User.builder().username("sinsa").build()
        );
        givenUsers.forEach((user -> userService.save(user)));
        // when
        List<User> users = userService.findAll();
        // then
        System.out.println(users);
        Assertions.assertThat(users)
                .isNotNull()
                .allSatisfy((user -> {
                    Assertions.assertThat(user.getId()).isNotNull().isNotZero();
                    Assertions.assertThat(user.getUsername()).isNotNull();
                    Assertions.assertThat(user.getCreatedAt()).isNotNull();
                }))
                .size()
                .isGreaterThanOrEqualTo(givenUsers.size());
    }

    @DisplayName("회원 정보 수정 - 가입일을 현재시각으로 변경")
    @Test
    public void test4() throws Exception {
        User user = User.builder()
                .username("leess")
                .build();
        userService.save(user); // id 발급
        System.out.println(user);

        // when
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        userService.update(user);
        // then
        User user2 = userService.findById(user.getId());
        System.out.println(user2);
        Assertions.assertThat(user2)
                .isNotNull()
                .satisfies((_user) -> {
                    Assertions.assertThat(_user.getId()).isEqualTo(user.getId());
                    Assertions.assertThat(_user.getUsername()).isEqualTo(user.getUsername());
                    Assertions.assertThat(_user.getCreatedAt()).isEqualTo(user.getCreatedAt());
                });
    }

    @DisplayName("회원 정보 수정 - 포인트 적립")
    @Test
    public void test5() throws Exception {
        User user = User.builder()
                .username("yoogs")
                .build();
        userService.save(user);
        System.out.println(user);

        // when
        Long id = user.getId();
        int pointToAdd = 500;
        userService.updatePoint(id, pointToAdd);
        // then
        User user2 = userService.findById(user.getId());
        System.out.println(user2);
        Assertions.assertThat(user2)
                .isNotNull()
                .satisfies((_user) -> {
                    Assertions.assertThat(_user.getId()).isEqualTo(user.getId());
                    Assertions.assertThat(_user.getUsername()).isEqualTo(user.getUsername());
                    Assertions.assertThat(_user.getCreatedAt()).isEqualTo(user.getCreatedAt());
                });
    }

    @DisplayName("사용자 삭제")
    @Test
    public void test6() throws Exception {
        // given
        User user = User.builder()
                .username("sejong")
                .build();
        userService.save(user); // id 발급
        System.out.println(user);
        // when
        Long id = user.getId();
        userService.delete(id);
        // then
        User user2 = userService.findById(id);
        Assertions.assertThat(user2).isNull();
    }
}