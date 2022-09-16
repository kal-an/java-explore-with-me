package event.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
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
    @DateTimeFormat
    private LocalDateTime eventDate;

    @NotNull(message = "Location should not be null")
    private Location location;

    private Boolean paid;

    @Min(value = 1, message = "Participant limit should greater then 0")
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
