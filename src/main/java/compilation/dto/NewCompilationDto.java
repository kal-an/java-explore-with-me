package compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    @NotNull(message = "Events IDs should not be null")
    private List<Integer> events;

    private Boolean pinned;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3, max = 100, message = "Title should less 100 and greater 3 characters")
    private String title;

}
