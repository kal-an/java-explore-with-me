package ru.yandex.practicum.explore.compilation.model;

import ru.yandex.practicum.explore.event.model.Event;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "compilations", schema = "public")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;

    @Column(nullable = false)
    private Boolean pinned;

    @Column(nullable = false)
    private String title;

}
