package ru.yandex.practicum.explore.event;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.explore.event.dto.*;
import ru.yandex.practicum.explore.event.model.State;
import ru.yandex.practicum.explore.user.model.User;

import javax.validation.constraints.Min;
import java.util.List;

public interface EventService {

    List<EventShortDto> getAllEvents(String text, List<Integer> categories, Boolean paid,
                                     String rangeStart, String rangeEnd,
                                     Boolean onlyAvailable, String sort,
                                     Integer from, Integer size);

    List<EventFullDto> getAllEvents(List<Integer> users, List<State> states,
                                    List<Integer> categories,
                                    String rangeStart, String rangeEnd,
                                    Integer from, Integer size);

    EventFullDto getEvent(Integer id);

    List<EventShortDto> getUserEvents(Integer userId,
                                      Integer from, Integer size);

    EventFullDto updateEvent(UpdateEventRequest updateDto);

    EventFullDto updateEvent(Integer eventId, AdminUpdateEventRequest updateDto);

    EventFullDto createEvent(NewEventDto dto, User user);

    EventFullDto cancelEvent(Integer eventId);

    EventFullDto publishEvent(Integer eventId);

    EventFullDto rejectEvent(Integer eventId);

    List<EventShortDto> getEventsForSubscriber(Integer subscriberId, Pageable pageable);
}
