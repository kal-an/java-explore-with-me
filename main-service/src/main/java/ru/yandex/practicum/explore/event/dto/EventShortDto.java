package ru.yandex.practicum.explore.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.yandex.practicum.explore.category.dto.CategoryDto;
import ru.yandex.practicum.explore.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {

    private Integer id;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 20, max = 2000, message = "Annotation should less 2000 and greater 20 characters")
    private String annotation;

    @NotNull(message = "Category ID should not be null")
    private CategoryDto category;

    private Long confirmedRequests;

    @NotBlank(message = "Event date should not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    @NotNull(message = "Initiator should not be null")
    private UserShortDto initiator;

    private boolean paid;

    @NotBlank(message = "Title should not be empty")
    @Size(min = 3, max = 120, message = "Title should less 120 and greater 3 characters")
    private String title;

    private Integer views;

}
