package ru.yandex.practicum.compilation.dto;

import lombok.*;
import ru.yandex.practicum.event.dto.EventShortDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompilationDto {

    private Long id;

    private List<EventShortDto> events;

    private Boolean pinned;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3, max = 100, message = "Title should less 100 and greater 3 characters")
    private String title;

}
