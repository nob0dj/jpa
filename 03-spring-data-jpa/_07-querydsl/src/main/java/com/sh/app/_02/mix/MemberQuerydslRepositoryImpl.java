package com.sh.app._02.mix;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sh.app._02.mix.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepositoryImpl implements MemberQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> findByGender(Gender gender) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.gender.eq(gender))
                .fetch();
    }
}
