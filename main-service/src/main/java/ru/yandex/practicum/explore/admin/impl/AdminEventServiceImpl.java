package ru.yandex.practicum.explore.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.explore.admin.AdminEventService;
import ru.yandex.practicum.explore.event.EventService;
import ru.yandex.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.model.State;

import java.util.List;

@Service
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {

    private final EventService eventService;

    public AdminEventServiceImpl(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public List<EventFullDto> getAllEvents(List<Integer> users,
                                           List<State> states, List<Integer> categories,
                                           String rangeStart, String rangeEnd,
                                           Integer from, Integer size) {
        return eventService.getAllEvents(users, states, categories,
                rangeStart, rangeEnd, from, size);
    }

    @Override
    public EventFullDto updateEvent(Integer eventId,
                                    AdminUpdateEventRequest updateDto) {
        return eventService.updateEventByAdmin(eventId, updateDto);
    }

    @Override
    public EventFullDto publishEvent(Integer eventId) {
        return eventService.publishEvent(eventId);
    }

    @Override
    public EventFullDto rejectEvent(Integer eventId) {
        return eventService.rejectEvent(eventId);
    }
}
