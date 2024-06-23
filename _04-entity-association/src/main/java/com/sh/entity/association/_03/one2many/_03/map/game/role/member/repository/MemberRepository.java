package com.sh.entity.association._03.one2many._03.map.game.role.member.repository;

import com.sh.entity.association._03.one2many._03.map.game.role.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
