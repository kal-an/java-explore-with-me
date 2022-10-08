package ru.yandex.practicum.explore.category;

import ru.yandex.practicum.explore.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
