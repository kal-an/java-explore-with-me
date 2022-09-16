package ru.yandex.practicum.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findByRequesterId(Integer id);

    List<Request> findByEventId(Integer id);

}
