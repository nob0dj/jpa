package com.sh.entity.mapping._03.element.collection._01.set._01.auth.repository;

import com.sh.entity.mapping._03.element.collection._01.set._01.auth.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;

    /*
        create table role (
            id varchar(255) not null,
            name varchar(255),
            primary key (id)
        ) engine=InnoDB

        create table role_permission (
            perm varchar(255),
            role_id varchar(255) not null
        ) engine=InnoDB
     */


    @DisplayName("Role-Permission 등록 및 조회")
    @Test
    public void test() throws Exception {
        // given
        String[] permissions = {"/admin/memberList.do", "/admin/updateMemberRole.do"};
        Role role = new Role("ROLE_ADMIN", "관리자", Set.of(permissions));
        // when
        Role role2 = roleRepository.save(role); // @GeneratedValue 사용하지 않으므로 영속컨텍스트에서 관리할 새 Role객체(값은 모두 동일)를 반환
        roleRepository.flush(); // test only
        // then
        assertThat(role2).isNotSameAs(role);

        Role role3 = roleRepository.findById(role.getId()).get(); // 영속성 컨텍스트에 entity객체가 있으므로 select쿼리 실행 안함
        assertThat(role3.getPermissions()).containsOnly(permissions); // 순서 상관없이 특정요소만 가지고 있는지 검사
    }

    @DisplayName("Role-Permission 수정")
    @Test
    public void test2() throws Exception {
        // given
        String[] permissions = {"/admin/memberList.do", "/admin/updateMemberRole.do"};
        Role role = new Role("ROLE_ADMIN", "관리자", Set.of(permissions));
        role = roleRepository.saveAndFlush(role); // @GeneratedValue 사용하지 않으므로 영속컨텍스트에서 관리할 새 Role객체(값은 모두 동일)를 반환
        // 수정시에는 Set객체 일부가 아닌 통째로 수정한다.
        String[] newPermissions = {"/admin/memberList.do", "/admin/updateMemberRole.do", "/admin/deleteMemeber.do"};
        // when
        role.setPermissions(Set.of(newPermissions));
        roleRepository.flush();
        // then
        Role role2 = roleRepository.findById(role.getId()).get(); // 영속성 컨텍스트에 entity객체가 있으므로 select쿼리 실행 안함
        assertThat(role2.getPermissions()).containsOnly(newPermissions); // 순서 상관없이 특정요소만 가지고 있는지 검사
    }

    @DisplayName("Role-Permission 삭제")
    @Test
    public void test3() throws Exception {
        // given
        String[] permissions = {"/admin/memberList.do", "/admin/updateMemberRole.do"};
        Role role = new Role("ROLE_ADMIN", "관리자", Set.of(permissions));
        role = roleRepository.saveAndFlush(role); // @GeneratedValue 사용하지 않으므로 영속컨텍스트에서 관리할 새 Role객체(값은 모두 동일)를 반환
        // when
        role.revokeAll();
        roleRepository.flush();
        // then
        Role role2 = roleRepository.findById(role.getId()).get(); // 영속성 컨텍스트에 entity객체가 있으므로 select쿼리 실행 안함
        assertThat(role2.getPermissions()).isNotNull().isEmpty();
    }
}