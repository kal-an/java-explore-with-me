package ru.yandex.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {

    @NotNull(message = "Id should not be null")
    private Integer id;

    @NotNull(message = "App should not be null")
    private String app;

    @NotNull(message = "Uri should not be null")
    private String uri;

    @NotNull(message = "IP should not be null")
    private String ip;

    @NotNull(message = "Timestamp should not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
