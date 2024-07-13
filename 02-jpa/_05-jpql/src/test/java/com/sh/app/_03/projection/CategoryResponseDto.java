package com.sh.app._03.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long categoryCode;
    private String categoryName;
    private Integer number;
}
