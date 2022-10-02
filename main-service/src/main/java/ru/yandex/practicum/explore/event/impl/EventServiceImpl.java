package ru.yandex.practicum.explore.event.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.explore.category.CategoryMapper;
import ru.yandex.practicum.explore.category.CategoryRepository;
import ru.yandex.practicum.explore.category.model.Category;
import ru.yandex.practicum.explore.event.EventMapper;
import ru.yandex.practicum.explore.event.EventNotFoundException;
import ru.yandex.practicum.explore.event.EventRepository;
import ru.yandex.practicum.explore.event.EventService;
import ru.yandex.practicum.explore.event.dto.*;
import ru.yandex.practicum.explore.event.model.Event;
import ru.yandex.practicum.explore.event.model.EventWithRequestsViews;
import ru.yandex.practicum.explore.event.model.State;
import ru.yandex.practicum.explore.exception.BadRequestException;
import ru.yandex.practicum.explore.exception.ForbiddenException;
import ru.yandex.practicum.explore.user.model.User;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EventServiceImpl(EventRepository eventRepository,
                            CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<EventShortDto> getAllEvents(String text, List<Integer> categories, Boolean paid,
                                            String rangeStart, String rangeEnd,
                                            Boolean onlyAvailable, String sort,
                                            @Min(0) Integer from, @Min(1) Integer size) {
        LocalDateTime start, end;
        if (rangeStart.isBlank() || rangeEnd.isBlank()) {
            start = LocalDateTime.now();
            end = LocalDateTime.MAX;
        } else {
            start = LocalDateTime.parse(rangeStart, DF);
            end = LocalDateTime.parse(rangeEnd, DF);
        }
        Sort sortBy;
        if (sort.equalsIgnoreCase("VIEWS")) {
            sortBy = Sort.by(Sort.Direction.DESC, "views");
        } else if (sort.equalsIgnoreCase("EVENT_DATE")) {
            sortBy = Sort.by(Sort.Direction.DESC, "eventDate");
        } else {
            throw new BadRequestException(String
                    .format("Incorrect request parameter, sort=%s", sort));
        }
        if (start.isAfter(end) || start.isEqual(end)) {
            log.error("Incorrect request parameter, rangeStart={}, rangeEnd={}",
                    rangeStart, rangeEnd);
            throw new BadRequestException(String
                    .format("Incorrect request parameter, rangeStart=%s, rangeEnd=%s",
                            rangeStart, rangeEnd));
        }
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, sortBy);
        return EventMapper.toShortDtoList(eventRepository.findAllEventsWithRequestsViews(text,
                categories, paid, onlyAvailable, start, end, pageable));
    }

    @Override
    public List<EventFullDto> getAllEvents(List<Integer> users,
                                           List<State> states, List<Integer> categories,
                                           String rangeStart, String rangeEnd,
                                           Integer from, Integer size) {
        int page = from / size;
        LocalDateTime start = LocalDateTime.parse(rangeStart, DF);
        LocalDateTime end = LocalDateTime.parse(rangeEnd, DF);
        Pageable pageable = PageRequest.of(page, size);
        return EventMapper.toFullDtoList(eventRepository.findAllEventsWithRequestsViews(users,
                states, categories, start, end, pageable));
    }

    @Override
    public EventFullDto getEvent(Integer id) {
        final EventWithRequestsViews eventInDb = eventRepository.findByEventIdWithRequestsViews(id)
                .orElseThrow(() -> new EventNotFoundException(String
                        .format("Event with id=%d was not found.", id)));
        log.info("Get event {}", eventInDb);
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public List<EventShortDto> getUserEvents(Integer userId,
                                             @Min(0) Integer from, @Min(1) Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return EventMapper.toShortDtoList(eventRepository
                .findByInitiatorIdWithRequestsViews(userId, pageable));
    }

    @Override
    public EventFullDto updateEvent(UpdateEventRequest updateDto) {
        final Event savedInDb = eventRepository.findById(updateDto.getEventId()).orElseThrow(() ->
                new EventNotFoundException(String.format("Event with id=%d was not found.",
                                updateDto.getEventId())));
        if (savedInDb.isPublished()
                || LocalDateTime.now().plusHours(2)
                .isAfter(LocalDateTime.parse(updateDto.getEventDate(), DF))) {
            log.error("Event id={} couldn't be updated", savedInDb.getId());
            throw new ForbiddenException(
                    String.format("Event id=%d couldn't be updated", savedInDb.getId()));
        }
        final Category newCategory = categoryRepository.findById(updateDto.getCategory())
                .orElseThrow(() -> new EventNotFoundException(String
                        .format("Category with id=%d was not found.",
                                updateDto.getCategory())));
        if (updateDto.getPaid() != null) savedInDb.setPaid(updateDto.getPaid());
        if (updateDto.getEventDate() != null)
            savedInDb.setEventDate(LocalDateTime.parse(updateDto.getEventDate(), DF));
        if (updateDto.getAnnotation() != null) savedInDb.setAnnotation(updateDto.getAnnotation());
        if (updateDto.getCategory() != null) savedInDb.setCategory(newCategory);
        if (updateDto.getDescription() != null)
            savedInDb.setDescription(updateDto.getDescription());
        if (updateDto.getParticipantLimit() != null)
            savedInDb.setParticipantLimit(updateDto.getParticipantLimit());
        if (updateDto.getRequestModeration() != null)
            savedInDb.setRequestModeration(updateDto.getRequestModeration());
        if (updateDto.getTitle() != null) savedInDb.setTitle(updateDto.getTitle());
        if (savedInDb.isCanceled()) {
            savedInDb.setState(State.PENDING);
        }
        final Event updatedEvent = eventRepository.save(savedInDb);
        log.info("Event {} updated", updatedEvent);
        final EventWithRequestsViews eventInDb = eventRepository
                .findByEventIdWithRequestsViews(savedInDb.getId()).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                savedInDb.getId())));
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public EventFullDto updateEventByAdmin(Integer eventId, AdminUpdateEventRequest updateDto) {
        final Event savedInDb = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(String
                        .format("Event with id=%d was not found.", eventId)));
        final Category newCategory = categoryRepository.findById(updateDto.getCategory())
                .orElseThrow(() -> new EventNotFoundException(String
                        .format("Category with id=%d was not found.", updateDto.getCategory())));
        if (updateDto.getPaid() != null) savedInDb.setPaid(updateDto.getPaid());
        if (updateDto.getEventDate() != null)
            savedInDb.setEventDate(LocalDateTime.parse(updateDto.getEventDate(), DF));
        if (updateDto.getAnnotation() != null) savedInDb.setAnnotation(updateDto.getAnnotation());
        if (updateDto.getCategory() != null) savedInDb.setCategory(newCategory);
        if (updateDto.getDescription() != null)
            savedInDb.setDescription(updateDto.getDescription());
        if (updateDto.getParticipantLimit() != null)
            savedInDb.setParticipantLimit(updateDto.getParticipantLimit());
        if (updateDto.getRequestModeration() != null)
            savedInDb.setRequestModeration(updateDto.getRequestModeration());
        if (updateDto.getTitle() != null) savedInDb.setTitle(updateDto.getTitle());
        if (updateDto.getLocation() != null) {
            savedInDb.setLat(updateDto.getLocation().getLat());
            savedInDb.setLon(updateDto.getLocation().getLon());
        }
        final Event updatedEvent = eventRepository.save(savedInDb);
        log.info("Event {} updated", updatedEvent);
        final EventWithRequestsViews eventInDb = eventRepository
                .findByEventIdWithRequestsViews(savedInDb.getId()).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                savedInDb.getId())));
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public EventFullDto createEvent(NewEventDto newDto, User user) {
        if (LocalDateTime.now().plusHours(2).isAfter(newDto.getEventDate())) {
            log.error("Event couldn't be created");
            throw new ForbiddenException("Event couldn't be created");
        }
        final Category category = categoryRepository.findById(newDto.getCategory())
                .orElseThrow(() -> new EventNotFoundException(String
                        .format("Category with id=%d was not found.",
                                newDto.getCategory())));
        final Event event = Event.builder()
                .annotation(newDto.getAnnotation())
                .category(CategoryMapper.toCategory(CategoryMapper.toDto(category)))
                .eventDate(newDto.getEventDate())
                .paid(newDto.isPaid())
                .title(newDto.getTitle())
                .description(newDto.getDescription())
                .createdOn(LocalDateTime.now())
                .participantLimit(newDto.getParticipantLimit())
                .requestModeration(newDto.getRequestModeration())
                .lat(newDto.getLocation().getLat())
                .lon(newDto.getLocation().getLon())
                .state(State.PENDING)
                .initiator(user)
                .build();
        final Event savedInDb = eventRepository.save(event);
        log.info("Event {} saved", savedInDb);
        final EventWithRequestsViews eventInDb = eventRepository
                .findByEventIdWithRequestsViews(savedInDb.getId()).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                savedInDb.getId())));
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public EventFullDto cancelEvent(Integer eventId) {
        final Event savedInDb = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(String
                        .format("Event with id=%d was not found.", eventId)));
        if (!savedInDb.isPending()) {
            log.error("Event id={} couldn't be canceled", savedInDb.getId());
            throw new ForbiddenException(
                    String.format("Event id=%d couldn't be canceled", savedInDb.getId()));
        }
        savedInDb.setState(State.CANCELED);
        final Event updatedEvent = eventRepository.save(savedInDb);
        log.info("Event {} canceled", updatedEvent);
        final EventWithRequestsViews eventInDb = eventRepository
                .findByEventIdWithRequestsViews(savedInDb.getId()).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                savedInDb.getId())));
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public EventFullDto publishEvent(Integer eventId) {
        final Event savedInDb = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(String
                        .format("Event with id=%d was not found.", eventId)));
        if (!savedInDb.isPending()
                || LocalDateTime.now().plusHours(1)
                .isAfter(savedInDb.getEventDate())) {
            log.error("Event id={} couldn't be published", savedInDb.getId());
            throw new ForbiddenException(
                    String.format("Event id=%d couldn't be published", savedInDb.getId()));
        }
        savedInDb.setState(State.PUBLISHED);
        savedInDb.setPublishedOn(LocalDateTime.now());
        final Event updatedEvent = eventRepository.save(savedInDb);
        log.info("Event {} published", updatedEvent);
        final EventWithRequestsViews eventInDb = eventRepository
                .findByEventIdWithRequestsViews(savedInDb.getId()).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                savedInDb.getId())));
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public EventFullDto rejectEvent(Integer eventId) {
        final Event savedInDb = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(String
                        .format("Event with id=%d was not found.", eventId)));
        if (savedInDb.isPublished()) {
            log.error("Event id={} couldn't be rejected", savedInDb.getId());
            throw new ForbiddenException(
                    String.format("Event id=%d couldn't be rejected", savedInDb.getId()));
        }
        savedInDb.setState(State.CANCELED);
        final Event updatedEvent = eventRepository.save(savedInDb);
        log.info("Event {} rejected", updatedEvent);
        final EventWithRequestsViews eventInDb = eventRepository
                .findByEventIdWithRequestsViews(savedInDb.getId()).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                savedInDb.getId())));
        return EventMapper.toFullDto(eventInDb);
    }

}
