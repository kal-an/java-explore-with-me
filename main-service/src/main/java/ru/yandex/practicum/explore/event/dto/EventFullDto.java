package ru.yandex.practicum.explore.event.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto extends EventShortDto {

    @NotBlank(message = "Description should not be empty")
    @Size(min = 20, max = 7000, message = "Description should less 7000 and greater 20 characters")
    private String description;

    @NotNull(message = "Location should not be null")
    private Location location;

    @NotBlank(message = "Created date should not be null")
    private String createdOn;

    @NotNull(message = "Limit should not be null")
    private Integer participantLimit;

    @NotBlank(message = "Published date should not be null")
    private String publishedOn;

    private Boolean requestModeration;

    @NotBlank(message = "State should not be empty")
    private String state;

    @Builder
    @Setter
    @Getter
    public static class Location {
        private Double lat;
        private Double lon;
    }

}
