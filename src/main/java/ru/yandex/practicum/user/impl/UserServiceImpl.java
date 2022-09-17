package ru.yandex.practicum.user.impl;

import ru.yandex.practicum.event.EventMapper;
import ru.yandex.practicum.event.EventService;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventRequest;
import ru.yandex.practicum.event.model.State;
import ru.yandex.practicum.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.request.RequestMapper;
import ru.yandex.practicum.request.RequestNotFoundException;
import ru.yandex.practicum.request.RequestRepository;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.request.model.Status;
import ru.yandex.practicum.user.UserNotFoundException;
import ru.yandex.practicum.user.UserRepository;
import ru.yandex.practicum.user.UserService;
import ru.yandex.practicum.user.model.User;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventService eventService;

    public UserServiceImpl(UserRepository userRepository,
                           RequestRepository requestRepository,
                           EventService eventService) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.eventService = eventService;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Integer userId) {
        return requestRepository.findByRequesterId(userId).stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(Integer userId,
                                                 Integer eventId) {
        final User userInDb = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        final EventFullDto eventDto = eventService.getEvent(eventId);
        if (userId.equals(userInDb.getId()) && eventId.equals(eventDto.getId())
            || eventDto.getInitiator().getId().equals(userId)
            || !eventDto.getState().equals(State.PUBLISHED.name())
            || eventDto.getConfirmedRequests() >= eventDto.getParticipantLimit()
            && eventDto.getParticipantLimit() > 0) {
            log.error("User id={} can't create request, eventId={}", userId, eventId);
            throw new ForbiddenException(
                    String.format("User id=%d can't create request, eventId=%d", userId, eventId));
        }
        final Request request = RequestMapper.toRequest(
                ParticipationRequestDto.builder()
                        .requester(userId)
                        .build());
        request.setRequester(userInDb);
        request.setEvent(EventMapper.toEvent(eventDto));
        request.setCreated(LocalDateTime.now());
        if (!eventDto.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
        }
        final Request savedRequest = requestRepository.save(request);
        log.info("Request {} saved", savedRequest);
        return RequestMapper.toDto(savedRequest);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId,
                                                 Integer requestId) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        final Request requestInDb = requestRepository.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException(String
                        .format("Request with id=%d was not found.", requestId)));
        requestInDb.setStatus(Status.CANCELED);
        final Request savedRequest = requestRepository.save(requestInDb);
        log.info("Request {} canceled", savedRequest);
        return RequestMapper.toDto(savedRequest);
    }

    @Override
    public List<EventShortDto> getEvents(Integer userId,
                                         @Min(0) Integer from, @Min(1) Integer size) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.getUserEvents(userId, from, size);
    }

    @Override
    public EventFullDto updateEvent(Integer userId,
                                    UpdateEventRequest updateDto) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.updateEvent(updateDto);
    }

    @Override
    public EventFullDto createEvent(Integer userId, NewEventDto newEventDto) {
        final User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.createEvent(newEventDto, user);
    }

    @Override
    public EventFullDto getEvent(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.getEvent(eventId);
    }

    @Override
    public EventFullDto cancelEvent(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.cancelEvent(eventId);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Integer userId,
                                                     Integer eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        eventService.getEvent(eventId);
        return requestRepository.findByEventId(eventId).stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(Integer userId,
                                                  Integer reqId, Integer eventId) {
        final EventFullDto eventDto = eventService.getEvent(eventId);
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));

        final Request requestInDb = requestRepository.findById(reqId).orElseThrow(() ->
                new RequestNotFoundException(String
                        .format("Request with id=%d was not found.", reqId)));
        requestInDb.setStatus(Status.CONFIRMED);
        final Request savedRequest = requestRepository.save(requestInDb);
        if (eventDto.getConfirmedRequests() + 1 == eventDto.getParticipantLimit()) {
            List<Request> requests = requestRepository.findByEventId(eventId);
            requests.stream()
                    .filter(request -> request.getStatus().equals(Status.PENDING))
                    .forEach(request -> request.setStatus(Status.CANCELED));
            requestRepository.saveAll(requests);
        }
        log.info("Request {} confirmed", savedRequest);
        return RequestMapper.toDto(savedRequest);
    }

    @Override
    public ParticipationRequestDto rejectRequest(Integer userId,
                                                 Integer reqId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        eventService.getEvent(eventId);
        final Request requestInDb = requestRepository.findById(reqId).orElseThrow(() ->
                new RequestNotFoundException(String
                        .format("Request with id=%d was not found.", reqId)));
        requestInDb.setStatus(Status.REJECTED);
        final Request savedRequest = requestRepository.save(requestInDb);
        log.info("Request {} rejected", savedRequest);
        return RequestMapper.toDto(savedRequest);
    }
}
