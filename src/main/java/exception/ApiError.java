package exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ApiError extends RuntimeException {

    private StackTraceElement[] stackTraceElement;
    private String message;
    private LocalDateTime timestamp;

}
