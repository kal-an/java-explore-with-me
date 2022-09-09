package user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends UserShortDto {

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    @Size(max = 150, message = "Email should less 150 characters")
    private String email;

}
