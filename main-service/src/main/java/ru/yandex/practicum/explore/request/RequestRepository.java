package ru.yandex.practicum.explore.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.explore.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findByRequesterId(Integer id);

    List<Request> findByEventId(Integer id);

    Optional<Request> findByEventIdAndRequesterId(Integer eventId, Integer requesterId);
}
