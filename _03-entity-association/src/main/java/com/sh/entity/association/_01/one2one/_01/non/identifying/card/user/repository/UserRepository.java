package com.sh.entity.association._01.one2one._01.non.identifying.card.user.repository;

import com.sh.entity.association._01.one2one._01.non.identifying.card.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
