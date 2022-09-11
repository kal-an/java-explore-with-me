package user;

import event.dto.EventFullDto;
import event.dto.EventShortDto;
import event.dto.NewEventDto;
import event.dto.UpdateEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private static final String X_HEADER_USER = "X-Header-User";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequests(
                @PathVariable Integer userId,
                @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Getting all user={} requests", userId);
        return userService.getRequests(userId, authUser);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto createRequest(
                @PathVariable Integer userId,
                @RequestParam Integer eventId,
                @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Create request eventId={}, userId={} ", eventId, userId);
        return userService.createRequest(userId, authUser, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
                @PathVariable Integer userId,
                @PathVariable Integer requestId,
                @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Cancel request={}, userId={} ", requestId, userId);
        return userService.cancelRequest(userId, authUser, requestId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEvents(
                @PathVariable Integer userId,
                @RequestHeader(X_HEADER_USER) Integer authUser,
                @RequestParam(required = false, defaultValue = "0") Integer from,
                @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting events user={}", userId);
        return userService.getEvents(userId, authUser, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(
                @PathVariable Integer userId,
                @RequestHeader(X_HEADER_USER) Integer authUser,
                @Valid @RequestBody UpdateEventRequest updateDto) {
        log.info("Update event user={}, requestDto={}", userId, updateDto);
        return userService.updateEvent(userId, authUser, updateDto);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(
                @PathVariable Integer userId,
                @RequestHeader(X_HEADER_USER) Integer authUser,
                @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Create event user={}, event={}", userId, newEventDto);
        return userService.createEvent(userId, authUser, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(
                @PathVariable Integer userId,
                @RequestHeader(X_HEADER_USER) Integer authUser,
                @PathVariable Integer eventId) {
        log.info("Getting event={} user={}", eventId, userId);
        return userService.getEvent(userId, authUser, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/cancel")
    public EventFullDto cancelEvent(
                @PathVariable Integer userId,
                @RequestHeader(X_HEADER_USER) Integer authUser,
                @PathVariable Integer eventId) {
        log.info("Cancel event={}, userId={} ", eventId, userId);
        return userService.cancelEvent(userId, authUser, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(
            @PathVariable Integer userId,
            @RequestHeader(X_HEADER_USER) Integer authUser,
            @PathVariable Integer eventId) {
        log.info("Getting requests, event={} user={}", eventId, userId);
        return userService.getRequests(userId, authUser, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(
            @PathVariable Integer userId,
            @PathVariable Integer reqId,
            @RequestHeader(X_HEADER_USER) Integer authUser,
            @PathVariable Integer eventId) {
        log.info("Confirm request={}, event={} user={}", reqId, eventId, userId);
        return userService.confirmRequest(userId, authUser, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(
            @PathVariable Integer userId,
            @PathVariable Integer reqId,
            @RequestHeader(X_HEADER_USER) Integer authUser,
            @PathVariable Integer eventId) {
        log.info("Reject request={}, event={} user={}", reqId, eventId, userId);
        return userService.rejectRequest(userId, authUser, eventId);
    }
}
