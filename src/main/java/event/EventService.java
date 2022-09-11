package event;

import event.dto.EventFullDto;
import event.dto.EventShortDto;

import java.util.List;

public interface EventService {

    List<EventShortDto> getAllEvents(String text, List<Integer> categories, Boolean paid,
                                     String rangeStart, String rangeEnd,
                                     Boolean onlyAvailable, String sort,
                                     Integer from, Integer size);

    EventFullDto getEvent(Integer id);
}
