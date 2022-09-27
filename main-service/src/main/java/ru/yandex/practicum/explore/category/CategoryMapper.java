package ru.yandex.practicum.explore.category;

import ru.yandex.practicum.explore.category.dto.CategoryDto;
import ru.yandex.practicum.explore.category.model.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toCategory(CategoryDto dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
