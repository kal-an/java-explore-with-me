package ru.yandex.practicum.event;


import ru.yandex.practicum.category.CategoryMapper;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventWithRequestsViews;
import ru.yandex.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventShortDto toShortDto(EventWithRequestsViews event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .eventDate(event.getEventDate().format(DF))
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .confirmedRequests(event.getRequests())
                .build();
    }

    public static EventFullDto toFullDto(EventWithRequestsViews event) {
        final EventFullDto dto = new EventFullDto();
        var location = EventFullDto.Location.builder()
                .lat(event.getLat())
                .lon(event.getLon())
                .build();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryDto.builder()
                .id(event.getCategory().getId())
                .name(event.getCategory().getName())
                .build());
        dto.setEventDate(event.getEventDate().format(DF));
        dto.setInitiator(UserShortDto.builder()
                .id(event.getInitiator().getId())
                .name(event.getInitiator().getName())
                .build());
        dto.setPaid(event.getPaid());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setCreatedOn(event.getCreatedOn().format(DF));
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setPublishedOn(event.getPublishedOn() == null ? null : event.getPublishedOn().format(DF));
        dto.setRequestModeration(event.getRequestModeration());
        dto.setState(event.getState().name());
        dto.setLocation(location);
        dto.setViews(event.getViews());
        dto.setConfirmedRequests(event.getRequests());
        return dto;
    }

    public static Event toEvent(EventFullDto dto) {
        return Event.builder()
                .id(dto.getId())
                .annotation(dto.getAnnotation())
                .category(CategoryMapper.toCategory(dto.getCategory()))
                .eventDate(LocalDateTime.parse(dto.getEventDate(), DF))
                .paid(dto.getPaid())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdOn(LocalDateTime.parse(dto.getCreatedOn(), DF))
                .participantLimit(dto.getParticipantLimit())
                .publishedOn(LocalDateTime.parse(dto.getPublishedOn(), DF))
                .requestModeration(dto.getRequestModeration())
                .lat(dto.getLocation().getLat())
                .lon(dto.getLocation().getLon())
                .build();
    }
}
