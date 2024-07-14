package com.sh.app.menu.dto;

import com.sh.app.menu.entity.Menu;
import com.sh.app.menu.entity.OrderableStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRegistRequestDto {
    @NotBlank
    private String menuName;
    @NotNull
    private Integer menuPrice;
    private Long categoryCode;
    @Pattern(regexp = "[YN]")
    private String orderableStatus;

    public Menu toMenu() {
        return Menu.builder()
                .menuName(this.menuName)
                .menuPrice(this.menuPrice)
                .categoryCode(this.categoryCode)
                .orderableStatus(OrderableStatus.valueOf(this.orderableStatus))
                .build();
    }
}
