package ru.yandex.practicum.provider;

import ru.yandex.practicum.event.client.dto.ViewStats;

import java.util.List;
import java.util.Map;

public interface StatsProvider {

    Map<String, ViewStats> getViewStats(String start, String end, List<String> uris,
                                         Boolean unique);

}
