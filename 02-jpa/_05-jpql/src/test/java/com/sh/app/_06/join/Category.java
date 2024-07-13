package com.sh.app._06.join;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Category06")
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
