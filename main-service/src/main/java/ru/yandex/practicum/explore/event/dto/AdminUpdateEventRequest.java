package ru.yandex.practicum.explore.event.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {

    private String annotation;

    private Integer category;

    private String description;

    private String eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;

    @Builder
    @Setter
    @Getter
    public static class Location{
        private Double lat;
        private Double lon;
    }

}
