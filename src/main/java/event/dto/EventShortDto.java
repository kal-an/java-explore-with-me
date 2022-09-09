package event.dto;

import category.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import user.dto.UserShortDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 20, max = 2000, message = "Annotation should less 2000 and greater 20 characters")
    private String annotation;

    @NotNull(message = "Category ID should not be null")
    private CategoryDto category;

    private Integer confirmedRequests;

    @NotEmpty(message = "Event date should not be empty")
    private String eventDate;

    @NotNull(message = "Initiator should not be null")
    private UserShortDto initiator;

    private Boolean paid;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3, max = 120, message = "Title should less 120 and greater 3 characters")
    private String title;

    private Integer views;

}
