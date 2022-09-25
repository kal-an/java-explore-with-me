package ru.yandex.practicum.stats.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.stats.StatRepository;
import ru.yandex.practicum.stats.StatsService;
import ru.yandex.practicum.stats.dto.EndpointHit;
import ru.yandex.practicum.stats.dto.ViewStats;
import ru.yandex.practicum.stats.model.Hit;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatRepository repository;
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsServiceImpl(StatRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startDate = null, endDate = null;
        if (!start.isEmpty())  {
            startDate = LocalDateTime.parse(decode(start), DF);
        }
        if (!end.isEmpty()) {
            endDate = LocalDateTime.parse(decode(end), DF);
        }
        return repository.findHitCount(startDate, endDate, uris).stream()
                .map(record -> new ViewStats(record.getApp(), record.getUri(), record.getHits()))
                .collect(Collectors.toList());
    }

    @Override
    public void addHit(EndpointHit newDto) {
        Hit hit = new Hit(
                newDto.getId(),
                newDto.getApp(),
                newDto.getUri(),
                newDto.getIp(),
                LocalDateTime.now());
        final Hit savedHit = repository.save(hit);
        log.info("Hit {} saved", savedHit);
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
