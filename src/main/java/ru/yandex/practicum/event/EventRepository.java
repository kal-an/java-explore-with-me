package ru.yandex.practicum.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventWithRequests;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer>, EventRepositoryCustom {

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as event_date, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as created_on, " +
            "e.participantLimit as participant_limit, e.publishedOn as published_on, " +
            "e.requestModeration as request_moderation, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e, Request r " +
            "left join e.category c " +
            "left join e.initiator u " +
            "where u.id = ?1 and r.event.id = e.id " +
            "group by e.id, c.id, u.id")
    List<EventWithRequests> findAllByInitiatorId(Integer userId, Pageable pageable);

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as eventDate, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as createdOn, " +
            "e.participantLimit as participantLimit, e.publishedOn as publishedOn, " +
            "e.requestModeration as requestModeration, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e, Request r " +
            "left join e.category c " +
            "left join e.initiator u " +
            "where r.event.id = ?1 and r.event.id = e.id " +
            "group by e.id, c.id, u.id")
    Optional<EventWithRequests> findByEventId(Integer eventId);

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as event_date, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as created_on, " +
            "e.participantLimit as participant_limit, e.publishedOn as published_on, " +
            "e.requestModeration as request_moderation, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e, Request r " +
            "left join e.category c " +
            "left join e.initiator u " +
            "where upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.description) like upper(concat('%', ?1, '%')) " +
            "and c.id in (?2) and e.paid = ?3 and e.state = 'PUBLISHED'" +
            "and e.eventDate >= ?4 and e.eventDate < ?5 " +
            "and r.event.id = e.id " +
            "group by e.id, c.id, u.id")
    List<EventWithRequests> findAllEvents(String text, List<Integer> categories, Boolean paid,
                                          String rangeStart, String rangeEnd,
                                          Pageable pageable);

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as event_date, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as created_on, " +
            "e.participantLimit as participant_limit, e.publishedOn as published_on, " +
            "e.requestModeration as request_moderation, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e, Request r " +
            "left join e.category c " +
            "left join e.initiator u " +
            "where e.initiator.id in (?1) and e.state in (?2) and e.category.id in (?3) " +
            "and e.eventDate >= ?4 and e.eventDate < ?5 " +
            "group by e.id ")
    List<EventWithRequests> findAllEvents(List<Integer> users, List<String> states,
                                          List<Integer> categories,
                                          String rangeStart, String rangeEnd, Pageable pageable);

}
