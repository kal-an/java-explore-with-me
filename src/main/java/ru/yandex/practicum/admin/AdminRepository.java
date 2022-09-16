package ru.yandex.practicum.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.user.model.User;

public interface AdminRepository extends JpaRepository<User, Integer> {

}
