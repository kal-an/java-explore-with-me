package ru.yandex.practicum.explore.compilation;

import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/compilations")
public class CompilationController {

    private final CompilationService compilationService;

    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping
    public List<CompilationDto> getCompilations(
                    @RequestParam Boolean pinned,
                    @RequestParam(required = false, defaultValue = "0") Integer from,
                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Getting all compilations");
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Integer compId) {
        log.info("Get compilation {}", compId);
        return compilationService.getCompilation(compId);
    }

}
