package ru.yandex.practicum.explore.admin;

import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.dto.NewCompilationDto;

public interface AdminCompilationService {

    CompilationDto createCompilation(NewCompilationDto newDto);

    void deleteCompilation(Integer compId);

    void deleteEventCompilation(Integer compId, Integer eventId);

    void addEventCompilation(Integer compId, Integer eventId);

    void unPinCompilation(Integer compId);

    void pinCompilation(Integer compId);

}
