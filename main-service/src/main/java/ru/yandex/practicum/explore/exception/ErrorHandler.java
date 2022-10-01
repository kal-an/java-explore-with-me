package ru.yandex.practicum.explore.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.explore.admin.AdminController;
import ru.yandex.practicum.explore.category.CategoryController;
import ru.yandex.practicum.explore.category.CategoryNotFoundException;
import ru.yandex.practicum.explore.compilation.CompilationController;
import ru.yandex.practicum.explore.compilation.CompilationNotFoundException;
import ru.yandex.practicum.explore.event.EventController;
import ru.yandex.practicum.explore.event.EventNotFoundException;
import ru.yandex.practicum.explore.request.RequestNotFoundException;
import ru.yandex.practicum.explore.user.UserController;
import ru.yandex.practicum.explore.user.UserNotFoundException;

import java.time.LocalDateTime;

@Slf4j
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
        log.error("Error {}", e.getMessage());
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
        log.error("Error {}", e.getMessage());
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(final RuntimeException e) {
        log.error("Error {}", e.getMessage());
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({ConflictEntityException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(final RuntimeException e) {
        log.error("Error {}", e.getMessage());
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalError(final Throwable e) {
        log.error("Error {}", e.getMessage());
        return new ApiError(e.getStackTrace(), e.getMessage(), LocalDateTime.now());
    }
}
