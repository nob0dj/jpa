package com.sh.app._09.nativequery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryResponseDto {
    private Long menuCode;
    private String menuName;
    private String categoryName;
}
