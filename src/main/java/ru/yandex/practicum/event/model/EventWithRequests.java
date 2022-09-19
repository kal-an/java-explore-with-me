package ru.yandex.practicum.event.model;

import java.time.LocalDateTime;

public interface EventWithRequests {

     Integer getId();

     String getAnnotation();

     CategoryView getCategory();

     LocalDateTime getEventDate();

     InitiatorView getInitiator();

     Boolean getPaid();

     String getTitle();

     String getDescription();

     LocalDateTime getCreatedOn();

     Integer getParticipantLimit();

     LocalDateTime getPublishedOn();

     Boolean getRequestModeration();

     State getState();

     Double getLat();

     Double getLon();

     Long getRequests();

     interface InitiatorView {

          Integer getId();

          String getName();

          String getEmail();
     }

     interface CategoryView {

          Integer getId();

          String getName();
     }

}
