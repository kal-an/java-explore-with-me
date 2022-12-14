package ru.yandex.practicum.explore.user;

import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.dto.EventShortDto;
import ru.yandex.practicum.explore.event.dto.NewEventDto;
import ru.yandex.practicum.explore.event.dto.UpdateEventRequest;
import ru.yandex.practicum.explore.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.explore.subscription.dto.SubscriptionDto;
import ru.yandex.practicum.explore.user.dto.UserShortDto;

import javax.validation.constraints.Min;
import java.util.List;

public interface UserService {

    List<ParticipationRequestDto> getRequests(Integer userId);

    ParticipationRequestDto createRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);

    List<EventShortDto> getEvents(Integer userId, @Min(0) Integer from, @Min(1) Integer size);

    EventFullDto updateEvent(Integer userId, UpdateEventRequest updateDto);

    EventFullDto createEvent(Integer userId, NewEventDto newEventDto);

    EventFullDto getEvent(Integer userId, Integer eventId);

    EventFullDto cancelEvent(Integer userId, Integer eventId);

    List<ParticipationRequestDto> getRequests(Integer userId, Integer eventId);

    ParticipationRequestDto confirmRequest(Integer userId,
                                           Integer reqId, Integer eventId);

    ParticipationRequestDto rejectRequest(Integer userId,
                                          Integer reqId, Integer eventId);

    List<UserShortDto> getUsers(@Min(0) Integer from, @Min(1) Integer size);

    void subscribeToUser(Integer userId, Integer otherId);

    void unSubscribe(Integer userId, Integer otherId);

    List<EventShortDto> getEventsForSubscriber(Integer subscriberId,
                                                @Min(0) Integer from, @Min(1) Integer size);

    List<SubscriptionDto> getSubscriptions(Integer subscriberId,
                                           @Min(0) Integer from, @Min(1) Integer size);
}
