package ru.yandex.practicum.explore.event.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.explore.event.EventRepository;
import ru.yandex.practicum.explore.event.EventRepositoryCustom;
import ru.yandex.practicum.explore.event.client.dto.ViewStats;
import ru.yandex.practicum.explore.event.model.EventWithRequests;
import ru.yandex.practicum.explore.event.model.EventWithRequestsViews;
import ru.yandex.practicum.explore.event.model.State;
import ru.yandex.practicum.explore.provider.StatsProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    private final EventRepository eventRepository;
    private final StatsProvider statsProvider;

    public EventRepositoryCustomImpl(@Lazy EventRepository eventRepository,
                                     @Lazy StatsProvider statsProvider) {
        this.eventRepository = eventRepository;
        this.statsProvider = statsProvider;
    }

    @Override
    public Optional<EventWithRequestsViews> findByEventIdWithRequestsViews(Integer eventId) {
        Optional<EventWithRequests> optional = eventRepository.findByEventId(eventId);
        if (optional.isPresent()) {
            String uri = "/events/" + eventId;
            List<String> uris = List.of(uri);
            Map<String, ViewStats> stats = statsProvider
                    .getViewStats("", "", uris, false);
            int views = stats.get(uri) != null ? stats.get(uri).getHits() : 0;
            return Optional.of(new EventWithRequestsViews(optional.get(), views));
        }
        return Optional.empty();
    }

    @Override
    public List<EventWithRequestsViews> findByEventIdWithRequestsViews(List<Integer> eventIds) {
        List<EventWithRequests> events = eventRepository.findByEventIds(eventIds);
        List<String> uriList = new ArrayList<>();
        List<EventWithRequestsViews> result = new ArrayList<>();
        events.forEach(event -> uriList.add("/events/" + event.getId()));
        Map<String, ViewStats> stats = statsProvider.getViewStats("", "", uriList, false);
        events.forEach(event -> {
            String uri = "/events/" + event.getId();
            int views = stats.get(uri) != null ? stats.get(uri).getHits() : 0;
            result.add(new EventWithRequestsViews(event, views));
        });
        return result;
    }

    @Override
    public List<EventWithRequestsViews> findByCompilationIdWithRequestsViews(Integer compilationId) {
        List<EventWithRequests> events =  eventRepository.findAllByCompilationId(compilationId);
        List<String> uriList = new ArrayList<>();
        List<EventWithRequestsViews> result = new ArrayList<>();
        events.forEach(event -> uriList.add("/events/" + event.getId()));
        Map<String, ViewStats> stats = statsProvider.getViewStats("", "", uriList, false);
        events.forEach(event -> {
            String uri = "/events/" + event.getId();
            int views = stats.get(uri) != null ? stats.get(uri).getHits() : 0;
            result.add(new EventWithRequestsViews(event, views));
        });
        return result;
    }

    @Override
    public List<EventWithRequestsViews> findByInitiatorIdWithRequestsViews(Integer userID,
                                                                      Pageable pageable) {
        List<EventWithRequests> events = eventRepository.findAllByInitiatorId(userID, pageable);
        List<String> uriList = new ArrayList<>();
        List<EventWithRequestsViews> result = new ArrayList<>();
        events.forEach(event -> uriList.add("/events/" + event.getId()));
        Map<String, ViewStats> stats = statsProvider.getViewStats("", "", uriList, false);
        events.forEach(event -> {
            String uri = "/events/" + event.getId();
            int views = stats.get(uri) != null ? stats.get(uri).getHits() : 0;
            result.add(new EventWithRequestsViews(event, views));
        });
        return result;
    }

    @Override
    public List<EventWithRequestsViews> findAllEventsWithRequestsViews(String text,
                                                                       List<Integer> categories,
                                                    Boolean paid, LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd, Pageable pageable) {
        List<EventWithRequests> events = eventRepository.findAllEvents(text, categories, paid,
                rangeStart, rangeEnd, pageable);
        List<String> uriList = new ArrayList<>();
        List<EventWithRequestsViews> result = new ArrayList<>();
        events.forEach(event -> uriList.add("/events/" + event.getId()));
        Map<String, ViewStats> stats = statsProvider.getViewStats("", "", uriList, false);
        events.forEach(event -> {
            String uri = "/events/" + event.getId();
            int views = stats.get(uri) != null ? stats.get(uri).getHits() : 0;
            result.add(new EventWithRequestsViews(event, views));
        });
        return result;
    }

    @Override
    public List<EventWithRequestsViews> findAllEventsWithRequestsViews(List<Integer> users,
                                                                       List<State> states,
                                                                       List<Integer> categories,
                                                                       LocalDateTime rangeStart,
                                                                       LocalDateTime rangeEnd,
                                                                       Pageable pageable) {
        List<EventWithRequests> events = eventRepository.findAllEvents(users, states, categories,
                rangeStart, rangeEnd, pageable);
        List<String> uriList = new ArrayList<>();
        List<EventWithRequestsViews> result = new ArrayList<>();
        events.forEach(event -> uriList.add("/events/" + event.getId()));
        Map<String, ViewStats> stats = statsProvider.getViewStats("", "", uriList, false);
        events.forEach(event -> {
            String uri = "/events/" + event.getId();
            int views = stats.get(uri) != null ? stats.get(uri).getHits() : 0;
            result.add(new EventWithRequestsViews(event, views));
        });
        return result;
    }

}
