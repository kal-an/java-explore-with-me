package ru.yandex.practicum.explore.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.explore.user.dto.UserDto;
import ru.yandex.practicum.explore.validation.OnCreate;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin")
public class AdminUserController {

    private final AdminUserService adminService;

    public AdminUserController(AdminUserService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(
            @RequestParam List<Integer> ids,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting all users");
        return adminService.getUsers(ids, from, size);
    }

    @PostMapping("/users")
    public UserDto addUser(
            @Validated(OnCreate.class) @RequestBody UserDto newDto) {
        log.info("Add new user {}", newDto);
        return adminService.addUser(newDto);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(
            @PathVariable Integer userId) {
        log.info("Delete user={}", userId);
        adminService.deleteUser(userId);
    }
}
