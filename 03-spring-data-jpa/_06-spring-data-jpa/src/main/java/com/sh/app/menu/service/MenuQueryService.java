package com.sh.app.menu.service;

import com.sh.app.menu.dto.MenuCategoryResponseDto;
import com.sh.app.menu.entity.Menu;
import com.sh.app.menu.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuQueryService {
    private final MenuRepository menuRepository;

    public Page<Menu> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    public MenuCategoryResponseDto findMenuAndCategory(Long menuCode) {
        return menuRepository.findMenuAndCategory(menuCode);
    }
}
