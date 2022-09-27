package ru.yandex.practicum.explore.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 20, max = 2000, message = "Annotation should less 2000 and greater 20 characters")
    private String annotation;

    @NotNull(message = "Category ID should not be null")
    private Integer category;

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 20, max = 7000, message = "Description should less 7000 and greater 20 characters")
    private String description;

    @NotNull(message = "Event date should not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(message = "Location should not be null")
    private Location location;

    private Boolean paid;

    @NotNull(message = "Limit should not be null")
    private Integer participantLimit;

    private Boolean requestModeration;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3, max = 120, message = "Title should less 120 and greater 3 characters")
    private String title;

    @Builder
    @Setter
    @Getter
    public static class Location{
        private Double lat;
        private Double lon;
    }

}
