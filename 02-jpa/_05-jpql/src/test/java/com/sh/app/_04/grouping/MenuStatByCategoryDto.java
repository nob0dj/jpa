package com.sh.app._04.grouping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuStatByCategoryDto {
    private Long categoryCode;
    private Double avgMenuPrice;
    private Long count;

    public MenuStatByCategoryDto(Long categoryCode, Double avgMenuPrice) {
        this.categoryCode = categoryCode;
        this.avgMenuPrice = avgMenuPrice;
    }

    public MenuStatByCategoryDto(Long categoryCode, Long count) {
        this.categoryCode = categoryCode;
        this.count = count;
    }
}
