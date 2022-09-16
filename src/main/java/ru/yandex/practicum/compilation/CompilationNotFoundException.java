package ru.yandex.practicum.compilation;

import ru.yandex.practicum.exception.NotFoundEntityException;

public class CompilationNotFoundException extends NotFoundEntityException {

    public CompilationNotFoundException(String message) {
        super(message);
    }
}
