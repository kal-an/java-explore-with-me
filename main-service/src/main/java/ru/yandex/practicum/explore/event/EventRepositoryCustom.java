package ru.yandex.practicum.explore.event;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.explore.event.model.EventWithRequestsViews;
import ru.yandex.practicum.explore.event.model.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepositoryCustom {

    Optional<EventWithRequestsViews> findByEventIdWithRequestsViews(Integer eventId);

    List<EventWithRequestsViews> findByEventIdWithRequestsViews(List<Integer> eventIds);

    List<EventWithRequestsViews> findByCompilationIdWithRequestsViews(Integer compilationId);

    List<EventWithRequestsViews> findByInitiatorIdWithRequestsViews(Integer userID,
                                                                    Pageable pageable);

    List<EventWithRequestsViews> findAllEventsWithRequestsViews(String text,
                                                                List<Integer> categories,
                                                                Boolean paid,
                                                                Boolean onlyAvailable,
                                                                LocalDateTime rangeStart,
                                                                LocalDateTime rangeEnd,
                                                                Pageable pageable);

    List<EventWithRequestsViews> findAllEventsWithRequestsViews(List<Integer> users,
                                                           List<State> states,
                                                           List<Integer> categories,
                                                                LocalDateTime rangeStart,
                                                                LocalDateTime rangeEnd,
                                                           Pageable pageable);

    List<EventWithRequestsViews> findByFollowerIdIdWithRequestsViews(Integer followerId,
                                                                      LocalDateTime currentTime,
                                                                      Pageable pageable);
}
