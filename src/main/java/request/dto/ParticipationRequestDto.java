package request.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
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
