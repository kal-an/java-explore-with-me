package ru.yandex.practicum.event.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class EventWithRequestsViews implements EventWithRequests {

    private Integer id;
    private String annotation;
    private CategoryView category;
    private LocalDateTime eventDate;
    private InitiatorView initiator;
    private Boolean paid;
    private String title;
    private String description;
    private LocalDateTime createdOn;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private Double lat;
    private Double lon;
    private Long requests;
    private Integer views;

    public EventWithRequestsViews(EventWithRequests event, Integer views) {
        this.id = event.getId();
        this.annotation = event.getAnnotation();
        this.category = event.getCategory();
        this.eventDate = event.getEventDate();
        this.initiator = event.getInitiator();
        this.paid = event.getPaid();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.createdOn = event.getCreatedOn();
        this.participantLimit = event.getParticipantLimit();
        this.publishedOn = event.getPublishedOn();
        this.requestModeration = event.getRequestModeration();
        this.state = event.getState();
        this.lat = event.getLat();
        this.lon = event.getLon();
        this.requests = event.getRequests();
        this.views = views;
    }
}
