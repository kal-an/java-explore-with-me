package ru.yandex.practicum.explore.admin;

import ru.yandex.practicum.explore.user.dto.UserDto;

import java.util.List;

public interface AdminUserService {

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    UserDto addUser(UserDto newDto);

    void deleteUser(Integer userId);
}
