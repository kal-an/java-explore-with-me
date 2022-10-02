package ru.yandex.practicum.explore.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.model.State;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin")
public class AdminEventController {

    private final AdminEventService adminEventService;

    public AdminEventController(AdminEventService adminEventService) {
        this.adminEventService = adminEventService;
    }

    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(
            @RequestParam List<Integer> users,
            @RequestParam List<State> states,
            @RequestParam List<Integer> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting all events");
        return adminEventService.getAllEvents(users, states, categories,
                rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Integer eventId,
            @RequestBody AdminUpdateEventRequest updateDto) {
        log.info("Update event={}, updateDto={}", eventId, updateDto);
        return adminEventService.updateEvent(eventId, updateDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(
            @PathVariable Integer eventId) {
        log.info("Publish event={}", eventId);
        return adminEventService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(
            @PathVariable Integer eventId) {
        log.info("Reject event={}", eventId);
        return adminEventService.rejectEvent(eventId);
    }
}
