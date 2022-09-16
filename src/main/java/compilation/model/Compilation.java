package compilation.model;

import event.model.Event;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "compilations", schema = "public")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "event_id")
    private Set<Event> events;

    @Column(nullable = false)
    private Boolean pinned;

    @Column(nullable = false)
    private String title;

}
