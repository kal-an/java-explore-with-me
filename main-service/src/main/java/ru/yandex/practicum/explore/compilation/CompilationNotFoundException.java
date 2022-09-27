package ru.yandex.practicum.explore.compilation;

import ru.yandex.practicum.explore.exception.NotFoundEntityException;

public class CompilationNotFoundException extends NotFoundEntityException {

    public CompilationNotFoundException(String message) {
        super(message);
    }
}
