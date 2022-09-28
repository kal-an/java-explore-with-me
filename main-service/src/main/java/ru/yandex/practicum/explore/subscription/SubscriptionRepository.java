package ru.yandex.practicum.explore.subscription;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.explore.subscription.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Optional<Subscription> findByUserIdAndFollowerId(Integer userId, Integer otherId);

    void deleteByUserIdAndFollowerId(Integer userId, Integer followerId);

    List<Subscription> findAllByFollowerId(Integer followerId, Pageable pageable);
}
