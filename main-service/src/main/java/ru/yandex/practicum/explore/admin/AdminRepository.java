package ru.yandex.practicum.explore.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.explore.user.model.User;

public interface AdminRepository extends JpaRepository<User, Integer> {

}
