package com.sh.app.menu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tbl_menu테이블과 1:1 매칭되는 entity 클래스
 * - (데이터지향관점) FK컬럼 tbl_menu.category_code를 실제 컬럼값으로 매칭했다.
 * - (객체지향관점) FK컬럼값이 아닌 실제 Category객체를 참조해야 한다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private Long menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode; // 데이터지향 관점
//    private Category category; // 객체지향 관점
    private String orderableStatus;
}
