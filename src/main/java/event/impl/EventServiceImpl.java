package event.impl;

import category.CategoryMapper;
import category.CategoryRepository;
import category.model.Category;
import event.EventMapper;
import event.EventNotFoundException;
import event.EventRepository;
import event.EventService;
import event.dto.*;
import event.model.Event;
import event.model.EventExtended;
import event.model.State;
import exception.BadRequestException;
import exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import user.model.User;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

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
        if (LocalDateTime.parse(rangeStart).isAfter(LocalDateTime.parse(rangeEnd))
            || LocalDateTime.parse(rangeStart).isEqual(LocalDateTime.parse(rangeEnd))) {
            log.error("Incorrect request parameter, rangeStart={}, rangeEnd={}",
                    rangeStart, rangeEnd);
            throw new BadRequestException(String
                    .format("Incorrect request parameter, rangeStart=%s, rangeEnd=%s",
                            rangeStart, rangeEnd));
        }
        if (rangeStart.isBlank() || rangeEnd.isBlank()) {
            rangeStart = LocalDateTime.now().format(DF);
            rangeEnd = LocalDateTime.MAX.format(DF);
        }
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findAllEvents(
                        text,categories, paid,
                        rangeStart, rangeEnd,
                        pageable)
                .stream()
                .map((EventExtended e) -> {
                    EventShortDto dto = EventMapper.toShortDto(e);
                    dto.setViews(1000);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<EventFullDto> getAllEvents(List<Integer> users,
                                           List<String> states, List<Integer> categories,
                                           String rangeStart, String rangeEnd,
                                           Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findAllEvents(users, states, categories,
                rangeStart, rangeEnd, pageable).stream()
                .map((EventExtended e) -> {
                    EventFullDto dto = EventMapper.toFullDto(e);
                    dto.setViews(1000);
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public EventFullDto getEvent(Integer id) {
        final EventExtended eventInDb =  eventRepository
                .findByEventId(id).orElseThrow(() ->
                new EventNotFoundException(String.format("Event with id=%d was not found.", id)));
        log.info("Get event {}", eventInDb);
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public List<EventShortDto> getUserEvents(Integer userId, Integer authUser,
                                             @Min(0) Integer from, @Min(1) Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findAllByInitiatorId(userId, pageable).stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(UpdateEventRequest updateDto) {
        final EventExtended eventInDb =  eventRepository
                .findByEventId(updateDto.getEventId()).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                updateDto.getEventId())));
        if (eventInDb.getState().equals(State.PUBLISHED)
                || LocalDateTime.now().plusHours(2)
                .isAfter(LocalDateTime.parse(updateDto.getEventDate()))) {
            log.error("Event id={} couldn't be updated", eventInDb.getId());
            throw new ForbiddenException(
                    String.format("Event id=%d couldn't be updated", eventInDb.getId()));
        }
        final Category newCategory = categoryRepository.findById(updateDto.getCategory())
                .orElseThrow(() -> new EventNotFoundException(String
                        .format("Category with id=%d was not found.",
                                updateDto.getCategory())));
        if (updateDto.getPaid() != null) eventInDb.setPaid(updateDto.getPaid());
        if (updateDto.getEventDate() != null) eventInDb.setEventDate(updateDto.getEventDate());
        if (updateDto.getAnnotation() != null) eventInDb.setAnnotation(updateDto.getAnnotation());
        if (updateDto.getCategory() != null) eventInDb.setCategory(newCategory);
        if (updateDto.getDescription() != null)
            eventInDb.setDescription(updateDto.getDescription());
        if (updateDto.getParticipantLimit() != null)
            eventInDb.setParticipantLimit(updateDto.getParticipantLimit());
        if (updateDto.getRequestModeration() != null)
            eventInDb.setRequestModeration(updateDto.getRequestModeration());
        if (updateDto.getTitle() != null) eventInDb.setTitle(updateDto.getTitle());
        if (eventInDb.getState().equals(State.CANCELED)) {
            eventInDb.setState(State.PENDING);
        }
        final EventExtended savedInDb = eventRepository.save(eventInDb);
        log.info("Event {} updated", savedInDb);
        return EventMapper.toFullDto(savedInDb);
    }

    @Override
    public EventFullDto updateEvent(Integer eventId, AdminUpdateEventRequest updateDto) {
        final EventExtended eventInDb =  eventRepository
                .findByEventId(eventId).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                eventId)));
        final Category newCategory = categoryRepository.findById(updateDto.getCategory())
                .orElseThrow(() -> new EventNotFoundException(String
                        .format("Category with id=%d was not found.", updateDto.getCategory())));
        if (updateDto.getPaid() != null) eventInDb.setPaid(updateDto.getPaid());
        if (updateDto.getEventDate() != null) eventInDb.setEventDate(updateDto.getEventDate());
        if (updateDto.getAnnotation() != null) eventInDb.setAnnotation(updateDto.getAnnotation());
        if (updateDto.getCategory() != null) eventInDb.setCategory(newCategory);
        if (updateDto.getDescription() != null)
            eventInDb.setDescription(updateDto.getDescription());
        if (updateDto.getParticipantLimit() != null)
            eventInDb.setParticipantLimit(updateDto.getParticipantLimit());
        if (updateDto.getRequestModeration() != null)
            eventInDb.setRequestModeration(updateDto.getRequestModeration());
        if (updateDto.getTitle() != null) eventInDb.setTitle(updateDto.getTitle());
        if (updateDto.getLocation() != null) {
            eventInDb.setLat(updateDto.getLocation().getLat());
            eventInDb.setLon(updateDto.getLocation().getLon());
        }
        final EventExtended savedInDb = eventRepository.save(eventInDb);
        log.info("Event {} updated", savedInDb);
        return EventMapper.toFullDto(savedInDb);
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
        final EventFullDto dto = new EventFullDto();
        dto.setPaid(newDto.getPaid());
        dto.setEventDate(newDto.getEventDate().format(DF));
        dto.setAnnotation(newDto.getAnnotation());
        dto.setCategory(CategoryMapper.toDto(category));
        dto.setDescription(newDto.getDescription());
        dto.setParticipantLimit(newDto.getParticipantLimit());
        dto.setRequestModeration(newDto.getRequestModeration());
        dto.setTitle(newDto.getTitle());
        dto.setLocation(EventFullDto.Location.builder()
                        .lon(newDto.getLocation().getLon())
                        .lat(newDto.getLocation().getLat())
                        .build());
        dto.setConfirmedRequests(0);
        dto.setState(State.PENDING.name());

        final Event event = EventMapper.toEvent(dto);
        event.setInitiator(user);
        event.setState(State.PUBLISHED);
        final Event savedInDb = eventRepository.save(event);
        final EventExtended eventInDb =  eventRepository
                .findByEventId(savedInDb.getId()).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                savedInDb.getId())));
        log.info("Event {} saved", savedInDb);
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public EventFullDto cancelEvent(Integer eventId) {
        final EventExtended eventInDb =  eventRepository
                .findByEventId(eventId).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                eventId)));
        if (!eventInDb.getState().equals(State.PENDING)) {
            log.error("Event id={} couldn't be canceled", eventInDb.getId());
            throw new ForbiddenException(
                    String.format("Event id=%d couldn't be canceled", eventInDb.getId()));
        }
        eventInDb.setState(State.CANCELED);
        final Event savedInDb = eventRepository.save(eventInDb);
        log.info("Event {} canceled", savedInDb);
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public EventFullDto publishEvent(Integer eventId) {
        final EventExtended eventInDb =  eventRepository
                .findByEventId(eventId).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                eventId)));
        if (!eventInDb.getState().equals(State.PENDING)
                || LocalDateTime.now().plusHours(1)
                .isAfter(LocalDateTime.parse(eventInDb.getEventDate()))) {
            log.error("Event id={} couldn't be published", eventInDb.getId());
            throw new ForbiddenException(
                    String.format("Event id=%d couldn't be published", eventInDb.getId()));
        }
        eventInDb.setState(State.PUBLISHED);
        eventInDb.setPublishedOn(LocalDateTime.now());
        final Event savedInDb = eventRepository.save(eventInDb);
        log.info("Event {} published", savedInDb);
        return EventMapper.toFullDto(eventInDb);
    }

    @Override
    public EventFullDto rejectEvent(Integer eventId) {
        final EventExtended eventInDb =  eventRepository
                .findByEventId(eventId).orElseThrow(() ->
                        new EventNotFoundException(String.format("Event with id=%d was not found.",
                                eventId)));
        if (eventInDb.getState().equals(State.PUBLISHED)) {
            log.error("Event id={} couldn't be rejected", eventInDb.getId());
            throw new ForbiddenException(
                    String.format("Event id=%d couldn't be rejected", eventInDb.getId()));
        }
        eventInDb.setState(State.REJECTED);
        final Event savedInDb = eventRepository.save(eventInDb);
        log.info("Event {} rejected", savedInDb);
        return EventMapper.toFullDto(eventInDb);
    }

}
