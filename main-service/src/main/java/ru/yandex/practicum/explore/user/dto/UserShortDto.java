package ru.yandex.practicum.explore.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserShortDto {

    private Integer id;

    @NotBlank(message = "Name should not be empty")
    @Size(max = 15, message = "Name should less 15 characters")
    private String name;

}
