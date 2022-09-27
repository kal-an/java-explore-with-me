package ru.yandex.practicum.explore.category;

import ru.yandex.practicum.explore.exception.NotFoundEntityException;

public class CategoryNotFoundException extends NotFoundEntityException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
