package ru.yandex.practicum.explore.subscription;

import ru.yandex.practicum.explore.exception.NotFoundEntityException;

public class SubscriptionNotFoundException extends NotFoundEntityException {

    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
