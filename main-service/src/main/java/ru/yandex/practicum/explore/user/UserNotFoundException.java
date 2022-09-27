package ru.yandex.practicum.explore.user;

import ru.yandex.practicum.explore.exception.NotFoundEntityException;

public class UserNotFoundException extends NotFoundEntityException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
