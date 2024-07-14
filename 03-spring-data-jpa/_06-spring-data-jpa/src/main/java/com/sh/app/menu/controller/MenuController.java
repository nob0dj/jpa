package com.sh.app.menu.controller;

import com.sh.app.common.paging.PageCriteria;
import com.sh.app.menu.dto.MenuCategoryResponseDto;
import com.sh.app.menu.entity.Menu;
import com.sh.app.menu.service.MenuQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
@Slf4j
public class MenuController {
    private final MenuQueryService menuQueryService;

    @GetMapping("/menuList")
    public void menuList(@PageableDefault(page = 1, size = 10) Pageable pageable, Model model){
        log.info("GET /menu/menuList?page={}", pageable.getPageNumber());
        // 사용자가 보게될 페이지가 0부터 시작하는 것은 아무래도 어색하다.
        // jpa Pageable처리에만 0부터 시작하는 인덱스를 사용하고, view단에서 사용할 페이지번호는 1부터 시작하도록 한다.
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());
        Page<Menu> menuPage = menuQueryService.findAll(pageable);
        log.debug("menus = {}", menuPage.getContent());
        model.addAttribute("menus", menuPage.getContent());

        int page = menuPage.getNumber() + 1;
        int limit = menuPage.getSize();
        int totalCount = (int) menuPage.getTotalElements();
        String url = "menuList"; // 상대주소
        model.addAttribute("pageCriteria", new PageCriteria(page, limit, totalCount, url));
    }

    @GetMapping("/menuDetail")
    public void menuDetail(@RequestParam Long menuCode, Model model) {
        MenuCategoryResponseDto menu = menuQueryService.findMenuAndCategory(menuCode);
        model.addAttribute("menu", menu);
    }
}
