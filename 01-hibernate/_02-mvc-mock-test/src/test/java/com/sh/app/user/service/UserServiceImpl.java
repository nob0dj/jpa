package com.sh.app.user.service;

import com.sh.app.user.entity.User;
import com.sh.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    /**
     * 영속상태의 entity를 수정하는 것만으로 session.commit()될때 변경감지한 부분에 대해 자동으로 쓰기처리된다.
     * @param id
     * @param point
     */
    @Override
    public void updatePoint(Long id, int point) {
        User user = userRepository.findById(id);
        user.setPoint(user.getPoint() + point);
    }

    @Override
    public void delete(Long id) {
       userRepository.delete(id);
    }
}
