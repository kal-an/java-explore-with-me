package ru.yandex.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.stats.model.Hit;

public interface StatRepository extends JpaRepository<Hit, Integer>, StatRepositoryCustom {


}
