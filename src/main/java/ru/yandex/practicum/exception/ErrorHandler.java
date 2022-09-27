package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.admin.AdminController;
import ru.yandex.practicum.category.CategoryController;
import ru.yandex.practicum.category.CategoryNotFoundException;
import ru.yandex.practicum.compilation.CompilationController;
import ru.yandex.practicum.compilation.CompilationNotFoundException;
import ru.yandex.practicum.event.EventController;
import ru.yandex.practicum.event.EventNotFoundException;
import ru.yandex.practicum.request.RequestNotFoundException;
import ru.yandex.practicum.user.UserController;
import ru.yandex.practicum.user.UserNotFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice(assignableTypes = {
        UserController.class,
        AdminController.class,
        CategoryController.class,
        CompilationController.class,
        EventController.class})
public class ErrorHandler {

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbidden(final RuntimeException e) {
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({
            CategoryNotFoundException.class,
            CompilationNotFoundException.class,
            EventNotFoundException.class,
            RequestNotFoundException.class,
            UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final RuntimeException e) {
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final RuntimeException e) {
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({ConflictEntityException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(final RuntimeException e) {
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }
}
