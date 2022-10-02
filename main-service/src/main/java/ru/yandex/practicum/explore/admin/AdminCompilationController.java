package ru.yandex.practicum.explore.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.dto.NewCompilationDto;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(path = "/admin")
public class AdminCompilationController {

    private final AdminCompilationService adminService;

    public AdminCompilationController(AdminCompilationService adminService) {
        this.adminService = adminService;
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
}
