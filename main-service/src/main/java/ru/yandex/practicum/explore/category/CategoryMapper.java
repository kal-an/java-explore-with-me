package ru.yandex.practicum.explore.category;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.explore.category.dto.CategoryDto;
import ru.yandex.practicum.explore.category.model.Category;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static List<CategoryDto> toDtoList(Iterable<Category> requests) {
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : requests) {
            dtos.add(toDto(category));
        }
        return dtos;
    }
}
