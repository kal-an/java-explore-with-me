package user;

import event.dto.EventFullDto;
import event.dto.EventShortDto;
import event.dto.NewEventDto;
import event.dto.UpdateEventRequest;
import request.dto.ParticipationRequestDto;

import java.util.List;

public interface UserService {

    List<ParticipationRequestDto> getRequests(Integer userId, Integer authUser);

    ParticipationRequestDto createRequest(Integer userId, Integer authUser, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer authUser, Integer requestId);

    List<EventShortDto> getEvents(Integer userId, Integer authUser, Integer from, Integer size);

    EventFullDto updateEvent(Integer userId, Integer authUser, UpdateEventRequest updateDto);

    EventFullDto createEvent(Integer userId, Integer authUser, NewEventDto newEventDto);

    EventFullDto getEvent(Integer userId, Integer authUser, Integer eventId);

    EventFullDto cancelEvent(Integer userId, Integer authUser, Integer eventId);

    List<ParticipationRequestDto> getRequests(Integer userId, Integer authUser, Integer eventId);

    ParticipationRequestDto confirmRequest(Integer userId, Integer authUser,
                                           Integer reqId, Integer eventId);

    ParticipationRequestDto rejectRequest(Integer userId, Integer authUser,
                                          Integer reqId, Integer eventId);
}
