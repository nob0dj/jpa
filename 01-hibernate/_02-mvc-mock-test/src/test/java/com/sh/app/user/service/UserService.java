package com.sh.app.user.service;

import com.sh.app.user.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findById(Long id);

    List<User> findAll();

    void update(User user);

    void updatePoint(Long id, int point);

    void delete(Long id);
}
