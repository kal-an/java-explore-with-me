package compilation;

import compilation.dto.CompilationDto;
import compilation.dto.NewCompilationDto;
import event.model.Event;

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
