package ru.yandex.practicum.category;

import ru.yandex.practicum.exception.NotFoundEntityException;

public class CategoryNotFoundException extends NotFoundEntityException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
