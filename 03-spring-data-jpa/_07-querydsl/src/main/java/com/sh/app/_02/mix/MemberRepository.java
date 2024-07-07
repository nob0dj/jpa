package com.sh.app._02.mix;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuerydslRepository {
}
