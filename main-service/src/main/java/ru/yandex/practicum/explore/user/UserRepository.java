package ru.yandex.practicum.explore.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.explore.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllUsers(Pageable pageable);
}
