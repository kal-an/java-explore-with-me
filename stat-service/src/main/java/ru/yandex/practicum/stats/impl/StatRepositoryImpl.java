package ru.yandex.practicum.stats.impl;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.stats.StatRepositoryCustom;
import ru.yandex.practicum.stats.model.App;
import ru.yandex.practicum.stats.model.Hit;
import ru.yandex.practicum.stats.model.HitCount;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class StatRepositoryImpl implements StatRepositoryCustom {

    private final EntityManager em;

    public StatRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<HitCount> findHitCount(LocalDateTime start, LocalDateTime end, List<String> uris) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HitCount> q = cb.createQuery(HitCount.class);
        Root<Hit> hit = q.from(Hit.class);
        Join<Hit, App> app = hit.join("app", JoinType.LEFT);
        q.multiselect(app.get("app"), hit.get("uri"), cb.count(hit));
        q.where(hit.get("uri").in(uris));

        if (start != null && end != null) q.where(
                cb.greaterThanOrEqualTo(hit.get("timestamp"), start),
                cb.lessThan(hit.get("timestamp"), end)
        );
        q.groupBy(app.get("app"), hit.get("uri"));
        return em.createQuery(q).getResultList();
    }


}
