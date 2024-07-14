package com.sh.app.menu.service;

import com.sh.app.menu.dto.MenuRegistRequestDto;
import com.sh.app.menu.entity.Menu;
import com.sh.app.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuCommandService {
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;


    public void regist(MenuRegistRequestDto menuRegistRequestDto) {
        // setter의 access level이 private이라면 값을 대입할 수 없다.
//        Menu menu = modelMapper.map(menuRegistRequestDto, Menu.class);
        Menu menu = menuRegistRequestDto.toMenu();
        menuRepository.save(menu);
    }
}
