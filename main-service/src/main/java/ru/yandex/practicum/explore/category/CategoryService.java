package ru.yandex.practicum.explore.category;

import ru.yandex.practicum.explore.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Integer id);

    CategoryDto updateCategory(CategoryDto updateDto);

    CategoryDto createCategory(CategoryDto newDto);

    void deleteCategory(Integer id);
}
