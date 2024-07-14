package com.sh.app._01.menu.repository;

import com.sh.app.menu.entity.Menu;
import com.sh.app.menu.repository.LegacyMenuRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <pre>
 * Spring환경에서는 EntityManagerFactory, EntityManager객체의 생명주기가 모두 Spring ApplicationContext하위에서 관리된다.
 * 서비스단에서 처리해야할 트랜잭션 범위설정, commit/rollback처리 모두 AOP를 이용해 별도로 분리되어 처리된다.
 * </pre>
 */
@SpringBootTest
class LegacyMenuRepositoryTest {
    @Autowired
    private LegacyMenuRepository legacyMenuRepository;
    
    
    @Test
    @DisplayName("메뉴 한건 조회")
    void test1() {
        // given
        Long menuCode = 10L;
        // when
        Menu menu = legacyMenuRepository.findByMenuCode(menuCode);
        /*
            Hibernate:
                select
                    m1_0.menu_code,
                    m1_0.category_code,
                    m1_0.menu_name,
                    m1_0.menu_price,
                    m1_0.orderable_status
                from
                    tbl_menu m1_0
                where
                    m1_0.menu_code=?
         */
        System.out.println(menu);
        // then
        assertThat(menu).isNotNull();
    }

    /**
     * <pre>
     * @Transactional를 통해서 트랜잭션 범위를 지정할 수 있다.
     * - 서비스단에서 작성해야 할 어노테이션이다.
     * - EntityManager#getTransaction().begin()
     * - EntityManager#getTransaction().commit()/rollback() 코드 역할을 한다.
     * - 스프링의 테스트에 @Transactional을 적용하면, 기본적으로 마지막에 rollback처리한다.
     * - @Rollback(false)를 통해서 DML 실제 적용할 수 있다. (rollback되지 않고, 쿼리도 확인 가능)
     * </pre>
     */
    @Transactional 
    @Rollback(false)
    @Test
    @DisplayName("메뉴 가격 수정")
    void test2() {
        // given
        Long menuCode = 10L;
        int newMenuPrice = 10000;
        // when
        legacyMenuRepository.updateMenuPrice(menuCode, newMenuPrice);

        // then
        Menu menu = legacyMenuRepository.findByMenuCode(menuCode);
        assertThat(menu.getMenuPrice()).isEqualTo(newMenuPrice);
    }
}