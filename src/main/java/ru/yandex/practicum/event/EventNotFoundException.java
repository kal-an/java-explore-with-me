package ru.yandex.practicum.event;

import ru.yandex.practicum.exception.NotFoundEntityException;

public class EventNotFoundException extends NotFoundEntityException {

    public EventNotFoundException(String message) {
        super(message);
    }
}
