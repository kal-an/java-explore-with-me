package ru.yandex.practicum.explore.user.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserShortDto {

    private Integer id;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 150, message = "Name should less 150 characters")
    private String name;

}