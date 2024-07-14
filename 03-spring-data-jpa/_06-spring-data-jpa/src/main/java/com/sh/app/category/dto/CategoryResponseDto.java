package com.sh.app.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long categoryCode;
    private String categoryName;
    private Long refCategoryCode;
}
