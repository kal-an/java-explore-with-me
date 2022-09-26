package ru.yandex.practicum.event.client.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ViewStats {

    private String app;

    private String uri;

    private Integer hits;
}
