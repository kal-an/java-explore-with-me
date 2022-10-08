package ru.yandex.practicum.explore.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.explore.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.explore.request.model.Request;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static List<ParticipationRequestDto> toDtoList(Iterable<Request> requests) {
        List<ParticipationRequestDto> dtos = new ArrayList<>();
        for (Request request : requests) {
            dtos.add(toDto(request));
        }
        return dtos;
    }
}
