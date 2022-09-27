package ru.yandex.practicum.explore.provider;

import ru.yandex.practicum.explore.event.client.dto.ViewStats;

import java.util.List;
import java.util.Map;

public interface StatsProvider {

    Map<String, ViewStats> getViewStats(String start, String end, List<String> uris,
                                         Boolean unique);

}
