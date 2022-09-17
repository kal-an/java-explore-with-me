package ru.yandex.practicum.event;


import ru.yandex.practicum.category.CategoryMapper;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventExtended;
import ru.yandex.practicum.user.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventShortDto toShortDto(EventExtended event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .eventDate(event.getEventDate().format(DF))
                .initiator(UserMapper.toDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .confirmedRequests(event.getRequestCount())
                .build();
    }

    public static EventFullDto toFullDto(EventExtended event) {
        final EventFullDto dto = new EventFullDto();
        var location = EventFullDto.Location.builder()
                .lat(event.getLat())
                .lon(event.getLon())
                .build();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryMapper.toDto(event.getCategory()));
        dto.setEventDate(event.getEventDate().format(DF));
        dto.setInitiator(UserMapper.toShortDto(event.getInitiator()));
        dto.setPaid(event.getPaid());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setCreatedOn(event.getCreatedOn());
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setPublishedOn(event.getPublishedOn());
        dto.setRequestModeration(event.getRequestModeration());
        dto.setState(event.getState().name());
        dto.setLocation(location);
        dto.setConfirmedRequests(event.getRequestCount());
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
                .createdOn(dto.getCreatedOn())
                .participantLimit(dto.getParticipantLimit())
                .publishedOn(dto.getPublishedOn())
                .requestModeration(dto.getRequestModeration())
                .lat(dto.getLocation().getLat())
                .lon(dto.getLocation().getLon())
                .build();
    }
}
