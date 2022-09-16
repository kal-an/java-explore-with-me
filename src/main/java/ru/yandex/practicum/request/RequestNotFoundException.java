package ru.yandex.practicum.request;

import ru.yandex.practicum.exception.NotFoundEntityException;

public class RequestNotFoundException extends NotFoundEntityException {

    public RequestNotFoundException(String message) {
        super(message);
    }
}
