package ru.yandex.practicum.user;

import ru.yandex.practicum.exception.NotFoundEntityException;

public class UserNotFoundException extends NotFoundEntityException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
