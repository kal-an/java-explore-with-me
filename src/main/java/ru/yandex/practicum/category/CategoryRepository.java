package ru.yandex.practicum.category;

import ru.yandex.practicum.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
