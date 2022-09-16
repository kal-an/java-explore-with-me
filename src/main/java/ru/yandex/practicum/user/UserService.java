package ru.yandex.practicum.user;

import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventRequest;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface UserService {

    List<ParticipationRequestDto> getRequests(Integer userId);

    ParticipationRequestDto createRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);

    List<EventShortDto> getEvents(Integer userId, Integer from, Integer size);

    EventFullDto updateEvent(Integer userId, UpdateEventRequest updateDto);

    EventFullDto createEvent(Integer userId, NewEventDto newEventDto);

    EventFullDto getEvent(Integer userId, Integer eventId);

    EventFullDto cancelEvent(Integer userId, Integer eventId);

    List<ParticipationRequestDto> getRequests(Integer userId, Integer eventId);

    ParticipationRequestDto confirmRequest(Integer userId,
                                           Integer reqId, Integer eventId);

    ParticipationRequestDto rejectRequest(Integer userId,
                                          Integer reqId, Integer eventId);
}
