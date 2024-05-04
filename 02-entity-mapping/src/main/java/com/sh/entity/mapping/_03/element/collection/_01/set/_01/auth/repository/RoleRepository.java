package com.sh.entity.mapping._03.element.collection._01.set._01.auth.repository;

import com.sh.entity.mapping._03.element.collection._01.set._01.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
