package ru.yandex.practicum.event;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.event.model.EventWithRequestsViews;

import java.util.List;
import java.util.Optional;

public interface EventRepositoryCustom {

    Optional<EventWithRequestsViews> findByEventIdWithRequestsViews(Integer eventId);

    List<EventWithRequestsViews> findByInitiatorIdWithRequestsViews(Integer userID,
                                                                    Pageable pageable);

    List<EventWithRequestsViews> findAllEventsWithRequestsViews(String text,
                                                           List<Integer> categories,
                                                           Boolean paid,
                                                           String rangeStart, String rangeEnd,
                                                           Pageable pageable);

    List<EventWithRequestsViews> findAllEventsWithRequestsViews(List<Integer> users,
                                                           List<String> states,
                                                           List<Integer> categories,
                                                           String rangeStart, String rangeEnd,
                                                           Pageable pageable);
}
