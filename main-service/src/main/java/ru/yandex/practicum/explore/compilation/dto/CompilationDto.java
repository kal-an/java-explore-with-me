package ru.yandex.practicum.explore.compilation.dto;

import lombok.*;
import ru.yandex.practicum.explore.event.dto.EventShortDto;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompilationDto {

    private Integer id;

    private List<EventShortDto> events;

    private Boolean pinned;

    private String title;

}
