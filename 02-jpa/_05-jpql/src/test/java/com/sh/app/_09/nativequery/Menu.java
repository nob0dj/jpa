package com.sh.app._09.nativequery;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Menu09")
@Table(name = "tbl_menu")
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "_09.nativequery.Menu.findByMenuCode",
            query = """
                select
                    *
                from
                    tbl_menu
                where
                    menu_code = ?
                """,
            resultClass = Menu.class
    ),
    @NamedNativeQuery(
            name = "_09.nativequery.Menu.findMenuAndCategory",
            query = """
                select
                    menu_code "menuCode",
                    menu_name "menuName",
                    (select category_name from tbl_category where category_code = m.category_code) "categoryName"
                from
                    tbl_menu m
                where
                    menu_code = ?
                """,
            resultSetMapping = "menuCategoryResponseDtoMap"
    )
})
@SqlResultSetMapping(
    name = "menuCategoryResponseDtoMap",
    classes = @ConstructorResult(
        targetClass = MenuCategoryResponseDto.class,
        columns = {
            @ColumnResult(name = "menuCode", type = Long.class),
            @ColumnResult(name = "menuName", type = String.class),
            @ColumnResult(name = "categoryName", type = String.class),
        }
    )
)
@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private Long menuCode;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_price")
    private int menuPrice;
    @Column(name = "category_code")
    private Long categoryCode;
    @Column(name = "orderable_status")
    private String orderableStatus;

}
