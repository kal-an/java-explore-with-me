package admin;

import category.dto.CategoryDto;
import compilation.dto.CompilationDto;
import compilation.dto.NewCompilationDto;
import event.dto.AdminUpdateEventRequest;
import event.dto.EventFullDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminService adminService;
    private static final String X_HEADER_USER = "X-Header-User";

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(
            @RequestParam List<Integer> users,
            @RequestParam List<String> states,
            @RequestParam List<Integer> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Getting all events");
        return adminService.getAllEvents(authUser, users, states, categories,
                rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Integer eventId,
            @RequestBody AdminUpdateEventRequest updateDto,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Update event={}, updateDto={}", eventId, updateDto);
        return adminService.updateEvent(authUser, eventId, updateDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(
            @PathVariable Integer eventId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Publish event={}", eventId);
        return adminService.publishEvent(authUser, eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(
            @PathVariable Integer eventId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Reject event={}", eventId);
        return adminService.rejectEvent(authUser, eventId);
    }

    @PatchMapping("/categories")
    public EventFullDto updateCategory(
            @Valid @RequestBody CategoryDto updateDto,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Update category updateDto={}", updateDto);
        return adminService.updateCategory(authUser, updateDto);
    }

    @PostMapping("/categories")
    public EventFullDto createCategory(
            @Valid @RequestBody CategoryDto newDto,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Create category updateDto={}", newDto);
        return adminService.createCategory(authUser, newDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(
            @PathVariable Integer catId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Delete category={}", catId);
        adminService.deleteCategory(authUser, catId);
    }

    @PostMapping("/compilations")
    public CompilationDto createCompilation(
            @Valid @RequestBody NewCompilationDto newDto,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Create compilation={}", newDto);
        return adminService.createCompilation(authUser, newDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilation(
            @PathVariable Integer compId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Delete compilation={}", compId);
        adminService.deleteCompilation(authUser, compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void deleteEventCompilation(
            @PathVariable Integer compId,
            @PathVariable Integer eventId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Delete event compilation={}, event={}", compId, eventId);
        adminService.deleteEventCompilation(authUser, compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventCompilation(
            @PathVariable Integer compId,
            @PathVariable Integer eventId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Add event compilation={}, event={}", compId, eventId);
        adminService.addEventCompilation(authUser, compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unPinCompilation(
            @PathVariable Integer compId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Unpin compilation={}", compId);
        adminService.unPinCompilation(authUser, compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(
            @PathVariable Integer compId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Pin compilation={}", compId);
        adminService.pinCompilation(authUser, compId);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(
            @RequestParam List<Integer> ids,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Getting all users");
        return adminService.getUsers(authUser, ids, from, size);
    }

    @PostMapping("/users")
    public UserDto addUser(
            @Valid @RequestBody UserDto newDto,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Add new user {}", newDto);
        return adminService.addUser(authUser, newDto);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(
            @PathVariable Integer userId,
            @RequestHeader(X_HEADER_USER) Integer authUser) {
        log.info("Delete user={}", userId);
        adminService.deleteUser(authUser, userId);
    }
}
