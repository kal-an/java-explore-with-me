package ru.yandex.practicum.explore.subscription;

import ru.yandex.practicum.explore.subscription.dto.SubscriptionDto;
import ru.yandex.practicum.explore.subscription.model.Subscription;
import ru.yandex.practicum.explore.user.UserMapper;

public class SubscriptionMapper {

    public static SubscriptionDto toDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .id(subscription.getId())
                .user(UserMapper.toShortDto(subscription.getUser()))
                .build();
    }
}