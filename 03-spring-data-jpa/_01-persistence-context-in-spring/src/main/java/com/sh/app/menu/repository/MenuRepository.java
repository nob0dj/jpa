package com.sh.app.menu.repository;

import com.sh.app.menu.entity.Menu;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepository {
    private final EntityManager entityManager;

    public Menu findByMenuCode(Long menuCode) {
        return entityManager.find(Menu.class, menuCode);
    }

    public void updateMenuPriceBy10Won(Long menuCode) {
        Menu menu = entityManager.find(Menu.class, menuCode);
        menu.setMenuPrice(menu.getMenuPrice() + 10);
    }
}
