package com.sh.entity.mapping._03.element.collection._01.set._02.auth.embeddable.repository;

import com.sh.entity.mapping._03.element.collection._01.set._02.auth.embeddable.entity.GrantedPermission;
import com.sh.entity.mapping._03.element.collection._01.set._02.auth.embeddable.entity.Role2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Role2RepositoryTest {
    @Autowired
    Role2Repository role2Repository;

    @DisplayName("Role-Permission 등록 및 조회")
    @Test
    public void test() throws Exception {
        // given
        GrantedPermission[] grantedPermissions = {
            new GrantedPermission("/admin/memberList.do", "honggd"),
            new GrantedPermission("/admin/updateMemberRole.do", "honggd")
        };
        Role2 role = new Role2("ROLE_ADMIN", "관리자", Set.of(grantedPermissions));
        // when
        Role2 role2 = role2Repository.save(role); // @GeneratedValue 사용하지 않으므로 영속컨텍스트에서 관리할 새 Role객체(값은 모두 동일)를 반환
        role2Repository.flush(); // test only
        // then
        assertThat(role2).isNotSameAs(role);

        Role2 role3 = role2Repository.findById(role.getId()).get(); // 영속성 컨텍스트에 entity객체가 있으므로 select쿼리 실행 안함
        assertThat(role3.getPermissions()).containsOnly(grantedPermissions); // 순서 상관없이 특정요소만 가지고 있는지 검사
    }
}