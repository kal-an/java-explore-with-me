package ru.yandex.practicum.explore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.explore.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
