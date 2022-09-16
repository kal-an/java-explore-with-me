package ru.yandex.practicum.event.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto extends EventShortDto {

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 20, max = 7000, message = "Description should less 7000 and greater 20 characters")
    private String description;

    @NotNull(message = "Location should not be null")
    private Location location;

    @NotNull(message = "Created date should not be null")
    private LocalDateTime createdOn;

    @Min(value = 1, message = "Participant limit should greater then 0")
    private Integer participantLimit;

    @NotNull(message = "Published date should not be null")
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    @NotEmpty(message = "State should not be empty")
    private String state;

    @Builder
    @Setter
    @Getter
    public static class Location{
        private Double lat;
        private Double lon;
    }

}
