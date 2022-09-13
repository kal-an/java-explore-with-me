package event;

import event.dto.EventFullDto;
import event.dto.EventShortDto;
import event.dto.NewEventDto;
import event.dto.UpdateEventRequest;
import user.model.User;

import java.util.List;

public interface EventService {

    List<EventShortDto> getAllEvents(String text, List<Integer> categories, Boolean paid,
                                     String rangeStart, String rangeEnd,
                                     Boolean onlyAvailable, String sort,
                                     Integer from, Integer size);

    EventFullDto getEvent(Integer id);

    List<EventShortDto> getUserEvents(Integer userId, Integer authUser,
                                      Integer from, Integer size);

    EventFullDto updateEvent(UpdateEventRequest dto);

    EventFullDto createEvent(NewEventDto dto, User user);

    EventFullDto cancelEvent(Integer eventId);
}
