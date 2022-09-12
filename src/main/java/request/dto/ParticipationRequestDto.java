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

    private Integer id;

    @NotNull(message = "Date created should not be null")
    private LocalDateTime created = LocalDateTime.now();

    @NotNull(message = "Event ID should not be null")
    private Integer event;

    @NotNull(message = "Requester ID should not be null")
    private Integer requester;

    @NotNull(message = "Status should not be null")
    private String status;
}
