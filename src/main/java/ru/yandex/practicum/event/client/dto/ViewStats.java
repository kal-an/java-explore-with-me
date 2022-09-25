package ru.yandex.practicum.event.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {

    private String app;

    private String uri;

    private Integer hits;
}
