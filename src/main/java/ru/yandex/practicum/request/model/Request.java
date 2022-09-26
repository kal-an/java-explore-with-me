package ru.yandex.practicum.request.model;

import ru.yandex.practicum.event.model.Event;
import lombok.*;
import ru.yandex.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "requests", schema = "public")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime created;

    @ManyToOne
    private Event event;

    @OneToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
