package ru.yandex.practicum.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewCompilationDto {

    @NotNull(message = "Events IDs should not be null")
    private List<Integer> events;

    private Boolean pinned;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3, max = 100, message = "Title should less 100 and greater 3 characters")
    private String title;

}
