package user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 150, message = "Name should less 150 characters")
    private String name;

}
