package ru.yandex.practicum.event;

import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventExtended;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("select new ru.yandex.practicum.event.model.EventExtended(e.id, e.annotation, " +
            "e.category, e.eventDate, e.initiator, e.paid, e.title, e.description, e.createdOn, " +
            "e.participantLimit, e.publishedOn, e.requestModeration, e.state, e.lat, e.lon, " +
            "count(r.id)) from Event as e " +
            "left join Request as r on e.id = r.event.id " +
            "where e.initiator.id = ?1 and r.status = 'CONFIRMED' " +
            "group by e.id")
    List<EventExtended> findAllByInitiatorId(Integer id, Pageable pageable);

    @Query("select new ru.yandex.practicum.event.model.EventExtended(e.id, e.annotation, " +
            "e.category, e.eventDate, e.initiator, e.paid, e.title, e.description, e.createdOn, " +
            "e.participantLimit, e.publishedOn, e.requestModeration, e.state, e.lat, e.lon, " +
            "count(r.id)) from Event as e " +
            "left join Request as r on e.id = r.event.id " +
            "where e.id = ?1 and r.status = 'CONFIRMED' " +
            "group by e.id")
    Optional<EventExtended> findByEventId(Integer eventId);

    @Query("select new ru.yandex.practicum.event.model.EventExtended(e.id, e.annotation, " +
            "e.category, e.eventDate, e.initiator, e.paid, e.title, e.description, e.createdOn, " +
            "e.participantLimit, e.publishedOn, e.requestModeration, e.state, e.lat, e.lon, " +
            "count(r.id)) from Event as e " +
            "left join Request as r on e.id = r.event.id " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and e.category.id in (?2) " +
            "and e.paid = ?3 " +
            "and e.eventDate >= ?4 " +
            "and e.eventDate < ?5 " +
            "and e.eventDate < ?5 " +
            "and r.status = 'CONFIRMED' " +
            "and e.state = 'PUBLISHED'" +
            "group by e.id")
    List<EventExtended> findAllEvents(String text, List<Integer> categories, Boolean paid,
                                                 String rangeStart, String rangeEnd,
                                                 Pageable pageable);

    @Query("select new ru.yandex.practicum.event.model.EventExtended(e.id, e.annotation, " +
            "e.category, e.eventDate, e.initiator, e.paid, e.title, e.description, e.createdOn, " +
            "e.participantLimit, e.publishedOn, e.requestModeration, e.state, e.lat, e.lon, " +
            "count(r.id)) from Event as e " +
            "left join Request as r on e.id = r.event.id " +
            "where " +
            "e.initiator in (?1) " +
            "and e.state in (?2) " +
            "and e.category in (?3) " +
            "and e.eventDate >= ?4 " +
            "and e.eventDate < ?5 " +
            "group by e.id ")
    List<EventExtended> findAllEvents(List<Integer> users, List<String> states,
                                      List<Integer> categories,
                                      String rangeStart, String rangeEnd, Pageable pageable);

}
