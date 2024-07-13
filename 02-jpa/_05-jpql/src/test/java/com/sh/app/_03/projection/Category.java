package com.sh.app._03.projection;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Category03")
@Table(name = "tbl_category")
@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_code")
    private Long categoryCode;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "ref_category_code")
    private Long refCategoryCode;

    public void changeCategoryName(String newCategoryName) {
        this.categoryName = newCategoryName;
    }
}
