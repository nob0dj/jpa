package com.sh.app.category.service;

import com.sh.app.category.dto.CategoryResponseDto;
import com.sh.app.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryQueryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> findByRefCategoryCodeIsNotNull() {
        return categoryRepository.findByRefCategoryCodeIsNotNull(Sort.by("categoryCode").ascending()).stream()
                .map((category) -> modelMapper.map(category, CategoryResponseDto.class))
                .toList();
    }
}
