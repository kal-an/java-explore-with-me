package event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {

    @Size(min = 20, max = 2000, message = "Annotation should less 2000 and greater 20 characters")
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000, message = "Description should less 2000 and greater 20 characters")
    private String description;

    private String eventDate;

    @NotNull(message = "EventId should not be null")
    private Integer eventId;

    private Boolean paid;

    @Min(value = 1, message = "Participant limit should greater then 0")
    private Integer participantLimit;

    private Boolean requestModeration;

    @Size(min = 3, max = 120, message = "Title should less 120 and greater 3 characters")
    private String title;

}
