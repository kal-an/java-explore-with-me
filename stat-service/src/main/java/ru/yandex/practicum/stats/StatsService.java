package ru.yandex.practicum.stats;

import ru.yandex.practicum.stats.dto.EndpointHit;
import ru.yandex.practicum.stats.dto.ViewStats;

import java.util.List;

public interface StatsService {

    List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique);

    void addHit(EndpointHit newDto);
}
