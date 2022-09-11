package category;

import category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Integer id);
}
