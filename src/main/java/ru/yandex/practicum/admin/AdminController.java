package ru.yandex.practicum.admin;

import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.event.dto.AdminUpdateEventRequest;
import ru.yandex.practicum.event.dto.EventFullDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.event.model.State;
import ru.yandex.practicum.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(
            @RequestParam List<Integer> users,
            @RequestParam List<State> states,
            @RequestParam List<Integer> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting all events");
        return adminService.getAllEvents(users, states, categories,
                rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Integer eventId,
            @RequestBody AdminUpdateEventRequest updateDto) {
        log.info("Update event={}, updateDto={}", eventId, updateDto);
        return adminService.updateEvent(eventId, updateDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(
            @PathVariable Integer eventId) {
        log.info("Publish event={}", eventId);
        return adminService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(
            @PathVariable Integer eventId) {
        log.info("Reject event={}", eventId);
        return adminService.rejectEvent(eventId);
    }

    @PatchMapping("/categories")
    public CategoryDto updateCategory(
            @Valid @RequestBody CategoryDto updateDto) {
        log.info("Update category updateDto={}", updateDto);
        return adminService.updateCategory(updateDto);
    }

    @PostMapping("/categories")
    public CategoryDto createCategory(
            @Valid @RequestBody CategoryDto newDto) {
        log.info("Create category createDto={}", newDto);
        return adminService.createCategory(newDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(
            @PathVariable Integer catId) {
        log.info("Delete category={}", catId);
        adminService.deleteCategory(catId);
    }

    @PostMapping("/compilations")
    public CompilationDto createCompilation(
            @Valid @RequestBody NewCompilationDto newDto) {
        log.info("Create compilation={}", newDto);
        return adminService.createCompilation(newDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilation(
            @PathVariable Integer compId) {
        log.info("Delete compilation={}", compId);
        adminService.deleteCompilation(compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void deleteEventCompilation(
            @PathVariable Integer compId,
            @PathVariable Integer eventId) {
        log.info("Delete event compilation={}, event={}", compId, eventId);
        adminService.deleteEventCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventCompilation(
            @PathVariable Integer compId,
            @PathVariable Integer eventId) {
        log.info("Add event compilation={}, event={}", compId, eventId);
        adminService.addEventCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unPinCompilation(
            @PathVariable Integer compId) {
        log.info("Unpin compilation={}", compId);
        adminService.unPinCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(
            @PathVariable Integer compId) {
        log.info("Pin compilation={}", compId);
        adminService.pinCompilation(compId);
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
            @Valid @RequestBody UserDto newDto) {
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
