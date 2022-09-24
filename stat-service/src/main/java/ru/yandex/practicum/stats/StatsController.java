package ru.yandex.practicum.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
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
                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting all events");
        return eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Integer id) {
        log.info("Get event {}", id);
        return eventService.getEvent(id);
    }

}
