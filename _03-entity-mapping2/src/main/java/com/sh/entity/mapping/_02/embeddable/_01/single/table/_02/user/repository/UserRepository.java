package com.sh.entity.mapping._02.embeddable._01.single.table._02.user.repository;

import com.sh.entity.mapping._02.embeddable._01.single.table._02.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
