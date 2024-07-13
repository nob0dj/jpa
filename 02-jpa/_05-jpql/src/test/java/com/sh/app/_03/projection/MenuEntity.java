package com.sh.app._03.projection;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Menu0302")
@Table(name = "tbl_menu")
@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private Long menuCode;
    @Embedded
    private MenuVo menuVo;
    @Column(name = "category_code")
    private Long categoryCode;
    @Column(name = "orderable_status")
    private String orderableStatus;

}
