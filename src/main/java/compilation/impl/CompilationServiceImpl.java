package compilation.impl;

import compilation.CompilationMapper;
import compilation.CompilationNotFoundException;
import compilation.CompilationRepository;
import compilation.CompilationService;
import compilation.dto.CompilationDto;
import compilation.dto.NewCompilationDto;
import compilation.model.Compilation;
import event.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return compilationRepository.findAllByPinned(pinned, pageable).stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(Integer id) {
        final Compilation compInDb = compilationRepository.findById(id).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", id)));
        return CompilationMapper.toDto(compInDb);
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newDto) {
        final Compilation compilation = CompilationMapper.toCompilation(
                CompilationDto.builder()
                        .events(newDto.getEvents())
                        .title(newDto.getTitle())
                        .pinned(newDto.getPinned())
                        .build());
        final Compilation savedCompilation = compilationRepository.save(compilation);
        log.info("Compilation {} created", savedCompilation);
        return CompilationMapper.toDto(savedCompilation);
    }

    @Override
    public void deleteCompilation(Integer compId) {
        final Compilation compInDb = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", compId)));
        compilationRepository.delete(compInDb);
        log.info("Compilation {} deleted", compId);
    }

    @Override
    public void deleteEventCompilation(Integer compId, Integer eventId) {
        final Compilation compInDb = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", compId)));
        compInDb.getEvents().removeIf(event -> event.getId().equals(eventId));
        final Compilation savedCompilation = compilationRepository.save(compInDb);
        log.info("Event {} removed from compilation {}", eventId, savedCompilation);
    }

    @Override
    public void addEventCompilation(Integer compId, Event newEvent) {
        final Compilation compInDb = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", compId)));
        compInDb.getEvents().add(newEvent);
        final Compilation savedCompilation = compilationRepository.save(compInDb);
        log.info("Event {} removed from compilation {}", newEvent, savedCompilation);
    }

    @Override
    public void unPinCompilation(Integer compId) {
        final Compilation compInDb = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", compId)));
        compInDb.setPinned(false);
        final Compilation savedCompilation = compilationRepository.save(compInDb);
        log.info("Compilation {} unpinned", savedCompilation);
    }

    @Override
    public void pinCompilation(Integer compId) {
        final Compilation compInDb = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", compId)));
        compInDb.setPinned(true);
        final Compilation savedCompilation = compilationRepository.save(compInDb);
        log.info("Compilation {} pinned", savedCompilation);
    }
}
