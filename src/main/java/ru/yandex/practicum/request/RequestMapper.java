package ru.yandex.practicum.request;

import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.model.Request;

public class RequestMapper {

    public static ParticipationRequestDto toDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .requester(request.getRequester().getId())
                .status(request.getStatus().name())
                .event(request.getEvent().getId())
                .build();
    }

    public static Request toRequest(ParticipationRequestDto dto) {
        return Request.builder()
                .id(dto.getId())
                .created(dto.getCreated())
                .build();
    }
}
