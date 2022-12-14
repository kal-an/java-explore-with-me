package ru.yandex.practicum.explore.user;

import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.dto.EventShortDto;
import ru.yandex.practicum.explore.event.dto.NewEventDto;
import ru.yandex.practicum.explore.event.dto.UpdateEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.explore.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.explore.subscription.dto.SubscriptionDto;
import ru.yandex.practicum.explore.user.dto.UserShortDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequests(
                @PathVariable Integer userId) {
        log.info("Getting all user={} requests", userId);
        return userService.getRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto createRequest(
                @PathVariable Integer userId,
                @RequestParam Integer eventId) {
        log.info("Create request eventId={}, userId={} ", eventId, userId);
        return userService.createRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
                @PathVariable Integer userId,
                @PathVariable Integer requestId) {
        log.info("Cancel request={}, userId={} ", requestId, userId);
        return userService.cancelRequest(userId, requestId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEvents(
                @PathVariable Integer userId,
                @RequestParam(required = false, defaultValue = "0") Integer from,
                @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting events user={}", userId);
        return userService.getEvents(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(
                @PathVariable Integer userId,
                @Valid @RequestBody UpdateEventRequest updateDto) {
        log.info("Update event user={}, requestDto={}", userId, updateDto);
        return userService.updateEvent(userId, updateDto);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(
                @PathVariable Integer userId,
                @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Create event user={}, event={}", userId, newEventDto);
        return userService.createEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(
                @PathVariable Integer userId,
                @PathVariable Integer eventId) {
        log.info("Getting event={} user={}", eventId, userId);
        return userService.getEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(
                @PathVariable Integer userId,
                @PathVariable Integer eventId) {
        log.info("Cancel event={}, userId={} ", eventId, userId);
        return userService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(
            @PathVariable Integer userId,
            @PathVariable Integer eventId) {
        log.info("Getting requests, event={} user={}", eventId, userId);
        return userService.getRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(
            @PathVariable Integer userId,
            @PathVariable Integer reqId,
            @PathVariable Integer eventId) {
        log.info("Confirm request={}, event={} user={}", reqId, eventId, userId);
        return userService.confirmRequest(userId, reqId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(
            @PathVariable Integer userId,
            @PathVariable Integer reqId,
            @PathVariable Integer eventId) {
        log.info("Reject request={}, event={} user={}", reqId, eventId, userId);
        return userService.rejectRequest(userId, reqId, eventId);
    }

    @GetMapping
    public List<UserShortDto> getUsers(
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting users");
        return userService.getUsers(from, size);
    }

    @PostMapping("/{userId}/subscriptions")
    public void subscribeToUser(
            @PathVariable Integer userId,
            @RequestParam Integer otherId) {
        log.info("Subscribe userId={}, otherId={}", userId, otherId);
        userService.subscribeToUser(userId, otherId);
    }

    @PatchMapping("/{userId}/subscriptions/{subscriberId}")
    public void unSubscribe(
            @PathVariable Integer userId,
            @PathVariable Integer subscriberId) {
        log.info("Unsubscribe userId={}, subscriberId={}", userId, subscriberId);
        userService.unSubscribe(userId, subscriberId);
    }

    @GetMapping("/events")
    public List<EventShortDto> getEventsForSubscriber(
            @RequestParam Integer subscriberId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting events, subscriberId={}", subscriberId);
        return userService.getEventsForSubscriber(subscriberId, from, size);
    }

    @GetMapping("/{userId}/subscriptions")
    public List<SubscriptionDto> getSubscriptions(
            @PathVariable Integer userId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting subscriptions, subscriberId={}", userId);
        return userService.getSubscriptions(userId, from, size);
    }
}
