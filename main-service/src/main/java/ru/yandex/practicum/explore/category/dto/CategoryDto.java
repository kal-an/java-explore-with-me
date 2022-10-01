package ru.yandex.practicum.explore.category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {

    private Integer id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 25, message = "Name should less 25 characters")
    private String name;

}