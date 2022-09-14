package user.impl;

import event.EventMapper;
import event.EventService;
import event.dto.EventFullDto;
import event.dto.EventShortDto;
import event.dto.NewEventDto;
import event.dto.UpdateEventRequest;
import event.model.State;
import exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import request.RequestMapper;
import request.RequestNotFoundException;
import request.RequestRepository;
import request.dto.ParticipationRequestDto;
import request.model.Request;
import request.model.Status;
import user.UserNotFoundException;
import user.UserRepository;
import user.UserService;
import user.model.User;

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
    public List<ParticipationRequestDto> getRequests(Integer userId, Integer authUser) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't to see requests", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't to see requests", userId));

        }
        return requestRepository.findByRequesterId(userId).stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(Integer userId, Integer authUser,
                                                 Integer eventId) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't create request", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't create request", userId));
        }
        final User userInDb = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        final EventFullDto eventDto = eventService.getEvent(eventId);
        if (userId.equals(userInDb.getId()) && eventId.equals(eventDto.getId())
            || eventDto.getInitiator().getId().equals(userId)
            || !eventDto.getState().equals(State.PUBLISHED.name())
            || eventDto.getConfirmedRequests() >= eventDto.getParticipantLimit()) {
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
        if (eventDto.getParticipantLimit() == 0 || !eventDto.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
        }
        final Request savedRequest = requestRepository.save(request);
        log.info("Request {} saved", savedRequest);
        return RequestMapper.toDto(savedRequest);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId, Integer authUser,
                                                 Integer requestId) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't cancel requestId={}", userId, requestId);
            throw new ForbiddenException(
                    String.format("User id=%d can't cancel requestId=%d", userId, requestId));
        }
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
    public List<EventShortDto> getEvents(Integer userId, Integer authUser,
                                         @Min(0) Integer from, @Min(1) Integer size) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't see events", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't see events", userId));
        }
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.getUserEvents(userId, authUser, from, size);
    }

    @Override
    public EventFullDto updateEvent(Integer userId, Integer authUser,
                                    UpdateEventRequest updateDto) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't update event", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't update event", userId));
        }
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.updateEvent(updateDto);
    }

    @Override
    public EventFullDto createEvent(Integer userId, Integer authUser, NewEventDto newEventDto) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't create event", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't create event", userId));
        }
        final User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.createEvent(newEventDto, user);
    }

    @Override
    public EventFullDto getEvent(Integer userId, Integer authUser, Integer eventId) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't see event", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't see event", userId));
        }
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.getEvent(eventId);
    }

    @Override
    public EventFullDto cancelEvent(Integer userId, Integer authUser, Integer eventId) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't cancel event", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't cancel event", userId));
        }
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        return eventService.cancelEvent(eventId);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Integer userId, Integer authUser,
                                                     Integer eventId) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't see requests", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't see requests", userId));
        }
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        eventService.getEvent(eventId);
        return requestRepository.findByEventId(eventId).stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(Integer userId, Integer authUser,
                                                  Integer reqId, Integer eventId) {
        final EventFullDto eventDto = eventService.getEvent(eventId);
        if (!userId.equals(authUser)
            || eventDto.getConfirmedRequests() >= eventDto.getParticipantLimit()) {
            log.error("User id={} can't confirm request", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't confirm request", userId));
        }
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
    public ParticipationRequestDto rejectRequest(Integer userId, Integer authUser,
                                                 Integer reqId, Integer eventId) {
        if (!userId.equals(authUser)) {
            log.error("User id={} can't reject request", userId);
            throw new ForbiddenException(
                    String.format("User id=%d can't reject request", userId));
        }
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
