package ru.yandex.practicum.event.model;

import lombok.*;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.compilation.model.Compilation;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events", schema = "public")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String annotation;

    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @OneToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User initiator;

    @Column(nullable = false)
    private Boolean paid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private Integer participantLimit;

    private LocalDateTime publishedOn;

    @Column(nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lon;

    @OneToMany(mappedBy = "event")
    private List<Request> requests;

    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "compilation_id"))
    private Set<Compilation> compilations;

}
