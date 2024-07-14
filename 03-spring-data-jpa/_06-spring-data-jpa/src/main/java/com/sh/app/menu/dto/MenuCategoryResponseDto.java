package com.sh.app.menu.dto;

import com.sh.app.menu.entity.OrderableStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryResponseDto {
    private Long menuCode;
    private String menuName;
    private int menuPrice;
    private String categoryName;
    private OrderableStatus orderableStatus;
}
