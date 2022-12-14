package ru.yandex.practicum.explore.event;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.explore.category.CategoryMapper;
import ru.yandex.practicum.explore.category.dto.CategoryDto;
import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.dto.EventShortDto;
import ru.yandex.practicum.explore.event.model.Event;
import ru.yandex.practicum.explore.event.model.EventWithRequestsViews;
import ru.yandex.practicum.explore.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static List<EventShortDto> toShortDtoList(Iterable<EventWithRequestsViews> events) {
        List<EventShortDto> dtos = new ArrayList<>();
        for (EventWithRequestsViews event : events) {
            dtos.add(toShortDto(event));
        }
        return dtos;
    }

    public static List<EventFullDto> toFullDtoList(Iterable<EventWithRequestsViews> events) {
        List<EventFullDto> dtos = new ArrayList<>();
        for (EventWithRequestsViews event : events) {
            dtos.add(toFullDto(event));
        }
        return dtos;
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
                .paid(dto.isPaid())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdOn(LocalDateTime.parse(dto.getCreatedOn(), DF))
                .participantLimit(dto.getParticipantLimit())
                .publishedOn(dto.getPublishedOn() == null ? null : LocalDateTime.parse(dto.getPublishedOn(), DF))
                .requestModeration(dto.getRequestModeration())
                .lat(dto.getLocation().getLat())
                .lon(dto.getLocation().getLon())
                .build();
    }
}
