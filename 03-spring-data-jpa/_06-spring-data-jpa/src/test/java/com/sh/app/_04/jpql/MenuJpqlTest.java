package com.sh.app._04.jpql;

import com.sh.app.menu.dto.IMenuCategoryResponseDto;
import com.sh.app.menu.dto.MenuCategoryResponseDto;
import com.sh.app.menu.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MenuJpqlTest {
    @Autowired
    private MenuRepository menuRepository;
    
    @Test
    @DisplayName("Menu-Category 조회")
    void test1() {
        // given
        Long menuCode = 20L;
        // when
        MenuCategoryResponseDto dto = menuRepository.findMenuAndCategory(menuCode);
        System.out.println(dto);
        // then
        assertThat(dto).isNotNull()
                .satisfies(
                    (_dto) -> assertThat(_dto.getMenuCode()).isEqualTo(menuCode),
                    (_dto) -> assertThat(_dto.getMenuName()).isNotNull(),
                    (_dto) -> assertThat(_dto.getCategoryName()).isNotNull()
                );

    }

    @Test
    @DisplayName("Menu-Category 조회 - NativeQuery + 인터페이스기반 DTO")
    void test2() {
        // given
        Long menuCode = 20L;
        // when
        IMenuCategoryResponseDto dto = menuRepository.findMenuAndCategoryWithSQL(menuCode);
        System.out.println(dto); // org.springframework.data.jpa.repository.query.AbstractJpaQuery$TupleConverter$TupleBackedMap@340ef431
        // then
        assertThat(dto).isNotNull()
                .satisfies(
                        (_dto) -> assertThat(_dto.getMenuCode()).isEqualTo(menuCode),
                        (_dto) -> assertThat(_dto.getMenuName()).isNotNull(),
                        (_dto) -> assertThat(_dto.getCategoryName()).isNotNull()
                );

    }
}
