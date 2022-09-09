package request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    private Long id;

    @NotNull(message = "Date created should not be null")
    private LocalDateTime created;

    @NotNull(message = "Event ID should not be null")
    private Long event;

    @NotNull(message = "Requester ID should not be null")
    private Long requester;

    @NotNull(message = "Status should not be null")
    private String status;
}
