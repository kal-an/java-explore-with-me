package ru.yandex.practicum.explore.user.dto;

import lombok.*;
import ru.yandex.practicum.explore.validation.OnCreate;
import ru.yandex.practicum.explore.validation.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserShortDto {

    @Null(groups = OnCreate.class, message = "ID should be empty")
    @NotNull(groups = OnUpdate.class, message = "ID should not be empty")
    private Integer id;

    @NotBlank(message = "Name should not be empty")
    @Size(max = 15, message = "Name should less 15 characters")
    private String name;

}
