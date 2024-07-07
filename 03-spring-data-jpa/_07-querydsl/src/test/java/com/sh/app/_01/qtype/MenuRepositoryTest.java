package com.sh.app._01.qtype;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MenuRepositoryTest {
    @Autowired
    MenuRepository menuRepository;

    @Test
    @DisplayName("QType 쿼리")
    void test() {
        // given
        Long categoryCode = 4L;
        // when
        List<Menu> menus = menuRepository.findByCategoryCode(categoryCode);
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
                m1_0.category_code=?
                and m1_0.orderable_status=?
            order by
                m1_0.menu_code
         */
        System.out.println(menus);
        // then
        assertThat(menus).isNotEmpty()
                .allMatch((menu) -> menu.getCategoryCode() == categoryCode);
    }
}