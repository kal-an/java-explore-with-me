package ru.yandex.practicum.explore.event.client.dto;

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
