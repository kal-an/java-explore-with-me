package ru.yandex.practicum.explore.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.explore.event.model.Event;
import ru.yandex.practicum.explore.event.model.EventWithRequests;
import ru.yandex.practicum.explore.event.model.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer>, EventRepositoryCustom {

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as eventDate, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as createdOn, " +
            "e.participantLimit as participant_limit, e.publishedOn as publishedOn, " +
            "e.requestModeration as request_moderation, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e " +
            "left join e.category c " +
            "left join e.initiator u " +
            "left join e.requests r " +
            "where u.id = ?1 " +
            "group by e.id, c.id, u.id")
    List<EventWithRequests> findAllByInitiatorId(Integer userId, Pageable pageable);

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as eventDate, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as createdOn, " +
            "e.participantLimit as participantLimit, e.publishedOn as publishedOn, " +
            "e.requestModeration as requestModeration, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e " +
            "left join e.category c " +
            "left join e.initiator u " +
            "left join e.requests r " +
            "where e.id = ?1 " +
            "group by e.id, c.id, u.id")
    Optional<EventWithRequests> findByEventId(Integer eventId);

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as eventDate, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as createdOn, " +
            "e.participantLimit as participantLimit, e.publishedOn as publishedOn, " +
            "e.requestModeration as requestModeration, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e " +
            "left join e.category c " +
            "left join e.initiator u " +
            "left join e.requests r " +
            "where e.id in (?1) " +
            "group by e.id, c.id, u.id " +
            "order by e.id desc")
    List<EventWithRequests> findByEventIds(List<Integer> eventIds);

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as eventDate, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as createdOn, " +
            "e.participantLimit as participant_limit, e.publishedOn as publishedOn, " +
            "e.requestModeration as request_moderation, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e " +
            "left join e.category c " +
            "left join e.initiator u " +
            "left join e.requests r " +
            "where (upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.description) like upper(concat('%', ?1, '%'))) " +
            "and c.id in (?2) and e.paid = ?3 and e.state = 'PUBLISHED'" +
            "and e.eventDate >= ?4 and e.eventDate < ?5 " +
            "group by e.id, c.id, u.id")
    List<EventWithRequests> findAllEvents(String text, List<Integer> categories, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                          Pageable pageable);

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as eventDate, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as createdOn, " +
            "e.participantLimit as participant_limit, e.publishedOn as publishedOn, " +
            "e.requestModeration as request_moderation, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e " +
            "left join e.category c " +
            "left join e.initiator u " +
            "left join e.requests r " +
            "where e.initiator.id in (?1) and e.state in (?2) and e.category.id in (?3) " +
            "and e.eventDate >= ?4 and e.eventDate < ?5 " +
            "group by e.id, c.id, u.id")
    List<EventWithRequests> findAllEvents(List<Integer> users, List<State> states,
                                          List<Integer> categories,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Pageable pageable);

    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as eventDate, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as createdOn, " +
            "e.participantLimit as participantLimit, e.publishedOn as publishedOn, " +
            "e.requestModeration as requestModeration, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Compilation comp " +
            "join comp.events as e " +
            "left join e.category c " +
            "left join e.initiator u " +
            "left join e.requests r " +
            "where comp.id = ?1 " +
            "group by e.id, c.id, u.id")
    List<EventWithRequests> findAllByCompilationId(Integer compilationId);


    @Query("select e.id as id, e.annotation as annotation, e.category as category, " +
            "e.eventDate as eventDate, e.initiator as initiator, e.paid as paid, " +
            "e.title as title, e.description as description, e.createdOn as createdOn, " +
            "e.participantLimit as participant_limit, e.publishedOn as publishedOn, " +
            "e.requestModeration as request_moderation, e.state as state, e.lat as lat, " +
            "e.lon as lon, count(r.id) as requests " +
            "from Event as e " +
            "left join e.category c " +
            "left join e.initiator u " +
            "left join e.requests r " +
            "left join Subscription s on s.user.id = u.id " +
            "where s.follower.id = ?1 and e.state = 'PUBLISHED' and e.eventDate > ?2 " +
            "group by e.id, c.id, u.id")
    List<EventWithRequests> findAllByFollowerId(Integer followerId, LocalDateTime currentTime,
                                                Pageable pageable);
}
