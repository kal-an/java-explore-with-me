package ru.yandex.practicum.event.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndpointHit {

    private Integer id;

    @NotNull(message = "App should not be null")
    private String app;

    @NotNull(message = "Uri should not be null")
    private String uri;

    @NotNull(message = "IP should not be null")
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
