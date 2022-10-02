package ru.yandex.practicum.explore.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.explore.admin.AdminCategoryService;
import ru.yandex.practicum.explore.category.CategoryService;
import ru.yandex.practicum.explore.category.dto.CategoryDto;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryService categoryService;

    public AdminCategoryServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto updateDto) {
        return categoryService.updateCategory(updateDto);
    }

    @Override
    public CategoryDto createCategory(CategoryDto newDto) {
        return categoryService.createCategory(newDto);
    }

    @Override
    public void deleteCategory(Integer catId) {
        categoryService.deleteCategory(catId);
    }

}
