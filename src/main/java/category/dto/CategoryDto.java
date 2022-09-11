package category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 25, message = "Name should less 25 characters")
    private String name;

}
