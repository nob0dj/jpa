package com.sh.app._03.projection;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MenuVo {
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_price")
    private int menuPrice;
}
