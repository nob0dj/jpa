package com.sh.app._02.mix;

import java.util.List;

public interface MemberQuerydslRepository {
    List<Member> findByGender(Gender gender);
}
