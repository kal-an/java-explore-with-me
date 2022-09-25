package ru.yandex.practicum.stats;

import ru.yandex.practicum.stats.model.HitCount;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepositoryCustom {

    List<HitCount> findHitCount(LocalDateTime start, LocalDateTime end, List<String> uris);
}
