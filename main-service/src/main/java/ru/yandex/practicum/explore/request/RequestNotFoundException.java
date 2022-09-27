package ru.yandex.practicum.explore.request;

import ru.yandex.practicum.explore.exception.NotFoundEntityException;

public class RequestNotFoundException extends NotFoundEntityException {

    public RequestNotFoundException(String message) {
        super(message);
    }
}
