package ru.yandex.practicum.explore.subscription;

import ru.yandex.practicum.explore.subscription.dto.SubscriptionDto;
import ru.yandex.practicum.explore.subscription.model.Subscription;
import ru.yandex.practicum.explore.user.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionMapper {

    public static SubscriptionDto toDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .id(subscription.getId())
                .user(UserMapper.toShortDto(subscription.getUser()))
                .build();
    }

    public static List<SubscriptionDto> toDtoList(Iterable<Subscription> subscriptions) {
        List<SubscriptionDto> dtos = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            dtos.add(toDto(subscription));
        }
        return dtos;
    }
}
