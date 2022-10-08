package ru.yandex.practicum.explore.admin;

import ru.yandex.practicum.explore.category.dto.CategoryDto;

public interface AdminCategoryService {

    CategoryDto updateCategory(CategoryDto updateDto);

    CategoryDto createCategory(CategoryDto newDto);

    void deleteCategory(Integer catId);
}
