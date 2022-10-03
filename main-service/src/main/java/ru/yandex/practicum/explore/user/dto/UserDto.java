package ru.yandex.practicum.explore.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto extends UserShortDto {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be empty")
    @Size(max = 30, message = "Email should less 30 characters")
    private String email;

}
