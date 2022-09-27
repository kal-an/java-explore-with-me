package ru.yandex.practicum.explore.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.explore.event.client.EventClient;
import ru.yandex.practicum.explore.event.client.dto.EndpointHit;
import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/events")
public class EventController {

    private final EventService eventService;
    private final EventClient client;
    @Value("${spring.application.name}")
    private String appName;

    public EventController(EventService eventService, EventClient client) {
        this.eventService = eventService;
        this.client = client;
    }

    @GetMapping
    public List<EventShortDto> getAllEvents(
                    @RequestParam String text,
                    @RequestParam List<Integer> categories,
                    @RequestParam Boolean paid,
                    @RequestParam(required = false) String rangeStart,
                    @RequestParam(required = false) String rangeEnd,
                    @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                    @RequestParam String sort,
                    @RequestParam(required = false, defaultValue = "0") Integer from,
                    @RequestParam(required = false, defaultValue = "10") Integer size,
                    HttpServletRequest request) {
        log.info("Getting all events");
        client.addHit(EndpointHit.builder()
                .app(appName)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .build());
        return eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Integer id, HttpServletRequest request) {
        log.info("Get event {}", id);
        client.addHit(EndpointHit.builder()
                .app(appName)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .build());
        return eventService.getEvent(id);
    }

}
