package ru.yandex.practicum.explore.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import ru.yandex.practicum.explore.subscription.SubscriptionMapper;
import ru.yandex.practicum.explore.subscription.SubscriptionRepository;
import ru.yandex.practicum.explore.subscription.dto.SubscriptionDto;
import ru.yandex.practicum.explore.subscription.model.Subscription;
import ru.yandex.practicum.explore.user.UserMapper;
import ru.yandex.practicum.explore.user.UserRepository;
import ru.yandex.practicum.explore.user.UserService;
import ru.yandex.practicum.explore.user.dto.UserShortDto;
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
    private final SubscriptionRepository subscriptionRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RequestRepository requestRepository,
                           EventService eventService,
                           SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.eventService = eventService;
        this.subscriptionRepository = subscriptionRepository;
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
                new NotFoundEntityException(String.format("User with id=%d not found.", userId)));
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
                new NotFoundEntityException(String.format("User with id=%d not found.", userId)));
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

    @Override
    public List<UserShortDto> getUsers(Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return UserMapper.toShortDtoList(userRepository.findAll(pageable));
    }

    @Override
    public void subscribeToUser(Integer userId, Integer otherId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundEntityException(String.format("User with id=%d not found.", userId)));
        User otherUser = userRepository.findById(otherId).orElseThrow(() ->
                new NotFoundEntityException(String.format("User with id=%d not found.", otherId)));
        final Optional<Subscription> optional = subscriptionRepository
                .findByUserIdAndFollowerId(userId, otherId);
        if (optional.isPresent()) {
            throw new ForbiddenException(
                    String.format("User id=%d couldn't be subscriber userId=%d", userId, otherId));
        }
        final Subscription subscription = Subscription.builder()
                .user(user)
                .follower(otherUser)
                .build();
        final Subscription savedInDb = subscriptionRepository.save(subscription);
        log.info("Subscription {} added", savedInDb);
    }

    @Override
    public void unSubscribe(Integer userId, Integer otherId) {
        final Optional<Subscription> optional = subscriptionRepository
                .findByUserIdAndFollowerId(userId, otherId);
        if (optional.isEmpty()) {
            throw new ForbiddenException(
                    String.format("User id=%d couldn't be unsubscribe userId=%d", otherId, userId));
        }
        subscriptionRepository.deleteById(optional.get().getId());
        log.info("Subscription {} deleted", optional.get().getId());
    }

    @Override
    public List<EventShortDto> getEventsForSubscriber(Integer subscriberId,
                                                       Integer from, Integer size) {
        userRepository.findById(subscriberId).orElseThrow(() ->
                new NotFoundEntityException(String
                        .format("User with id=%d was not found.", subscriberId)));
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return eventService.getEventsForSubscriber(subscriberId, pageable);
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(Integer subscriberId,
                                                  Integer from, Integer size) {
        userRepository.findById(subscriberId).orElseThrow(() ->
                new NotFoundEntityException(String.format("User with id=%d not found.", subscriberId)));
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return SubscriptionMapper
                .toDtoList(subscriptionRepository.findAllByFollowerId(subscriberId, pageable));
    }

}
