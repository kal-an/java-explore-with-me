package ru.yandex.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.stats.model.App;

import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Integer> {

    Optional<App> findByApp(String app);
}
