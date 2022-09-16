package event;

import event.model.Event;
import event.model.EventExtended;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<EventExtended> findAllByInitiatorId(Integer id, Pageable pageable);

    @Query("select new ru.yandex.practicum.event.model.EventExtended(e.id, e.title, " +
            "e.description, e.created_on, " +
            "e.event_date, e.lat, e.lon, e.annotation, e.paid " +
            "e.creator_id, e.participant_limit, e.published_on " +
            "e.published_on, e.request_moderation, e.state, e.category_id, " +
            "count(r.id)) from event as e " +
            "left join requests as r on e.id = r.event_id " +
            "group by e.id " +
            "where e.id = ?1 and r.status = 'CONFIRMED'")
    Optional<EventExtended> findByEventId(Integer eventId);

    @Query("select new ru.yandex.practicum.event.model.EventExtended(e.id, e.title, " +
            "e.description, e.created_on, " +
            "e.event_date, e.lat, e.lon, e.annotation, e.paid " +
            "e.creator_id, e.participant_limit, e.published_on " +
            "e.published_on, e.request_moderation, e.state, e.category_id, " +
            "count(r.id)) from event as e " +
            "left join requests as r on e.id = r.event_id " +
            "group by e.id " +
            "where " +
            "upper(i.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(i.description) like upper(concat('%', ?1, '%')) " +
            "and e.category_id in (?2) " +
            "and paid = ?3 " +
            "and e.event_date >= ?4 " +
            "and e.event_date < ?5 " +
            "and e.event_date < ?5 " +
            "and r.status = 'CONFIRMED' " +
            "and state = 'PUBLISHED'")
    List<EventExtended> findAllEvents(String text, List<Integer> categories, Boolean paid,
                                                 String rangeStart, String rangeEnd,
                                                 Pageable pageable);

    @Query("select new ru.yandex.practicum.event.model.EventExtended(e.id, e.title, " +
            "e.description, e.created_on, " +
            "e.event_date, e.lat, e.lon, e.annotation, e.paid " +
            "e.creator_id, e.participant_limit, e.published_on " +
            "e.published_on, e.request_moderation, e.state, e.category_id, " +
            "count(r.id)) from event as e " +
            "left join requests as r on e.id = r.event_id " +
            "group by e.id " +
            "where " +
            "e.creator_id in (?1) " +
            "e.state in (?2) " +
            "e.category_id in (?3) " +
            "and e.event_date >= ?4 " +
            "and e.event_date < ?5")
    List<EventExtended> findAllEvents(List<Integer> users, List<String> states,
                                      List<Integer> categories,
                                      String rangeStart, String rangeEnd, Pageable pageable);

}
