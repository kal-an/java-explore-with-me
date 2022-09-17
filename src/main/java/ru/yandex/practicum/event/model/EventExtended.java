package ru.yandex.practicum.event.model;

import lombok.*;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventExtended extends Event {

    private Long requestCount;

    public EventExtended(Integer id, String annotation, Category category, LocalDateTime eventDate,
                         User initiator, Boolean paid, String title,
                         String description, LocalDateTime createdOn, Integer participantLimit,
                         LocalDateTime publishedOn, Boolean requestModeration, State state,
                         Double lat, Double lon, Long requestCount) {
        super(id, annotation, category, eventDate, initiator, paid, title, description, createdOn,
                participantLimit, publishedOn, requestModeration, state, lat, lon);
        this.requestCount = requestCount;
    }
}
