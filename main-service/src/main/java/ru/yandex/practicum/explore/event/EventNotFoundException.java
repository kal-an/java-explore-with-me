package ru.yandex.practicum.explore.event;

import ru.yandex.practicum.explore.exception.NotFoundEntityException;

public class EventNotFoundException extends NotFoundEntityException {

    public EventNotFoundException(String message) {
        super(message);
    }
}
