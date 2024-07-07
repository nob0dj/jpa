package com.sh.app._02.menu.crud;

import com.sh.app.menu.entity.Menu;
import com.sh.app.menu.repository.MenuRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴 한건 조회")
    void test1() {
        // given
        Long menuCode = 1L;
        // when
        Menu menu = menuRepository.findByMenuCode(menuCode);
        /*
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
        // then
        Assertions.assertThat(menu)
                .isNotNull()
                .satisfies(
                    (_menu) -> Assertions.assertThat(_menu.getMenuCode()).isEqualTo(menuCode),
                    (_menu) -> Assertions.assertThat(_menu.getMenuName()).isNotNull(),
                    (_menu) -> Assertions.assertThat(_menu.getMenuPrice()).isNotZero(),
                    (_menu) -> Assertions.assertThat("Y".equals(_menu.getOrderableStatus()) || "N".equals(_menu.getOrderableStatus()))
                );
    }
}