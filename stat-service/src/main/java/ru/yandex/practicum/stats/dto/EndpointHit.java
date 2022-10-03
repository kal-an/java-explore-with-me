package ru.yandex.practicum.stats.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EndpointHit {

    private Integer id;

    @NotNull(message = "App should not be null")
    private String app;

    @NotNull(message = "Uri should not be null")
    private String uri;

    @NotNull(message = "IP should not be null")
    private String ip;

    private String timestamp;
}
