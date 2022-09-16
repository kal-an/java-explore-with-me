package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.user.UserController;

import java.time.LocalDateTime;

@RestControllerAdvice(assignableTypes = {
        UserController.class})
public class ErrorHandler {

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbidden(final RuntimeException e) {
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }
}
