package ru.yandex.practicum.explore.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.explore.admin.AdminRepository;
import ru.yandex.practicum.explore.admin.AdminUserService;
import ru.yandex.practicum.explore.user.UserMapper;
import ru.yandex.practicum.explore.user.UserNotFoundException;
import ru.yandex.practicum.explore.user.dto.UserDto;
import ru.yandex.practicum.explore.user.model.User;

import java.util.List;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminRepository adminRepository;

    public AdminUserServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids,
                                  Integer from, Integer size) {
        if (!ids.isEmpty()) {
            return UserMapper.toDtoList(adminRepository.findAllById(ids));
        } else {
            int page = from / size;
            Pageable pageable = PageRequest.of(page, size);
            return UserMapper.toDtoList(adminRepository.findAll(pageable));
        }
    }

    @Override
    public UserDto addUser(UserDto newDto) {
        final User user = UserMapper.toUser(newDto);
        final User savedUser = adminRepository.save(user);
        log.info("User {} saved", savedUser);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        final User user = adminRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        adminRepository.delete(user);
    }

}
