package ru.yandex.practicum.explore.subscription.dto;

import lombok.*;
import ru.yandex.practicum.explore.user.dto.UserShortDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SubscriptionDto {

    private Integer id;

    private UserShortDto user;
}
