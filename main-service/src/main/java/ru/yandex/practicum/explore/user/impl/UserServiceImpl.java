package ru.yandex.practicum.explore.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.explore.event.EventMapper;
import ru.yandex.practicum.explore.event.EventService;
import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.dto.EventShortDto;
import ru.yandex.practicum.explore.event.dto.NewEventDto;
import ru.yandex.practicum.explore.event.dto.UpdateEventRequest;
import ru.yandex.practicum.explore.event.model.State;
import ru.yandex.practicum.explore.exception.ForbiddenException;
import ru.yandex.practicum.explore.exception.NotFoundEntityException;
import ru.yandex.practicum.explore.request.RequestMapper;
import ru.yandex.practicum.explore.request.RequestRepository;
import ru.yandex.practicum.explore.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.explore.request.model.Request;
import ru.yandex.practicum.explore.request.model.Status;
import ru.yandex.practicum.explore.user.UserRepository;
import ru.yandex.practicum.explore.user.UserService;
import ru.yandex.practicum.explore.user.model.User;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        return RequestMapper.toDtoList(requestRepository.findByRequesterId(userId));
    }

    @Override
    public ParticipationRequestDto createRequest(Integer userId,
                                                 Integer eventId) {
        final User userInDb = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        final EventFullDto eventDto = eventService.getEvent(eventId);
        final Optional<Request> optionalRequest = requestRepository
                .findByEventIdAndRequesterId(eventId, userId);

        if (optionalRequest.isPresent()
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
        log.info("Request {} saved", savedRequest.getId());
        return RequestMapper.toDto(savedRequest);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId,
                                                 Integer requestId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        final Request requestInDb = requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("Request with id=%d was not found.", requestId)));
        requestInDb.setStatus(Status.CANCELED);
        final Request savedRequest = requestRepository.save(requestInDb);
        log.info("Request {} canceled", savedRequest.getId());
        return RequestMapper.toDto(savedRequest);
    }

    @Override
    public List<EventShortDto> getEvents(Integer userId,
                                         @Min(0) Integer from, @Min(1) Integer size) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.getUserEvents(userId, from, size);
    }

    @Override
    public EventFullDto updateEvent(Integer userId,
                                    UpdateEventRequest updateDto) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.updateEvent(updateDto);
    }

    @Override
    public EventFullDto createEvent(Integer userId, NewEventDto newEventDto) {
        final User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.createEvent(newEventDto, user);
    }

    @Override
    public EventFullDto getEvent(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.getEvent(eventId);
    }

    @Override
    public EventFullDto cancelEvent(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.cancelEvent(eventId);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Integer userId,
                                                     Integer eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        eventService.getEvent(eventId);
        return RequestMapper.toDtoList(requestRepository.findByEventId(eventId));
    }

    @Override
    public ParticipationRequestDto confirmRequest(Integer userId,
                                                  Integer reqId, Integer eventId) {
        final EventFullDto eventDto = eventService.getEvent(eventId);
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));

        final Request requestInDb = requestRepository.findById(reqId).orElseThrow(() ->
                new NotFoundEntityException(String
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
        log.info("Request {} confirmed", savedRequest.getId());
        return RequestMapper.toDto(savedRequest);
    }

    @Override
    public ParticipationRequestDto rejectRequest(Integer userId,
                                                 Integer reqId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", userId)));
        eventService.getEvent(eventId);
        final Request requestInDb = requestRepository.findById(reqId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("Request with id=%d was not found.", reqId)));
        requestInDb.setStatus(Status.REJECTED);
        final Request savedRequest = requestRepository.save(requestInDb);
        log.info("Request {} rejected", savedRequest.getId());
        return RequestMapper.toDto(savedRequest);
    }
}
