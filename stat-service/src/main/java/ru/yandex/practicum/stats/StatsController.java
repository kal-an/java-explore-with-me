package ru.yandex.practicum.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.stats.dto.EndpointHit;
import ru.yandex.practicum.stats.dto.ViewStats;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam List<String> uris,
            @RequestParam Boolean unique) {
        log.info("Getting stats for uris {}", uris);
        return statsService.getStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public void addHit(@Valid @RequestBody EndpointHit newDto) {
        log.info("Adding new hit {}", newDto);
        statsService.addHit(newDto);
    }

}
