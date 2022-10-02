package ru.yandex.practicum.explore.admin;

import ru.yandex.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.model.State;

import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getAllEvents(List<Integer> users,
                                    List<State> states, List<Integer> categories,
                                    String rangeStart, String rangeEnd,
                                    Integer from, Integer size);

    EventFullDto updateEvent(Integer eventId, AdminUpdateEventRequest updateDto);

    EventFullDto publishEvent(Integer eventId);

    EventFullDto rejectEvent(Integer eventId);

}
