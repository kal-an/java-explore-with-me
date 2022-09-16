package ru.yandex.practicum.exception;

public class ConflictEntityException extends RuntimeException {

    public ConflictEntityException(String message) {
        super(message);
    }
}
