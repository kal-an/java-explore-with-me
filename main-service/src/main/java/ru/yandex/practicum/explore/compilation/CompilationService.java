package ru.yandex.practicum.explore.compilation;

import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.explore.event.model.Event;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilation(Integer id);

    CompilationDto createCompilation(NewCompilationDto newDto);

    void deleteCompilation(Integer compId);

    void deleteEventCompilation(Integer compId, Integer eventId);

    void addEventCompilation(Integer compId, Event newEvent);

    void unPinCompilation(Integer compId);

    void pinCompilation(Integer compId);

}
