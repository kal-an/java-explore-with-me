package ru.yandex.practicum.explore.compilation.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.explore.compilation.CompilationMapper;
import ru.yandex.practicum.explore.compilation.CompilationNotFoundException;
import ru.yandex.practicum.explore.compilation.CompilationRepository;
import ru.yandex.practicum.explore.compilation.CompilationService;
import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.explore.compilation.model.Compilation;
import ru.yandex.practicum.explore.event.EventMapper;
import ru.yandex.practicum.explore.event.EventRepository;
import ru.yandex.practicum.explore.event.dto.EventShortDto;
import ru.yandex.practicum.explore.event.model.Event;
import ru.yandex.practicum.explore.event.model.EventWithRequestsViews;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository,
                                  EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        final List<Compilation> compilations = compilationRepository
                .findAllByPinned(pinned, pageable);
        final List<CompilationDto> result = new ArrayList<>();
        for (Compilation compilation : compilations) {
            final List<EventWithRequestsViews> eventsCustom = eventRepository
                    .findByCompilationIdWithRequestsViews(compilation.getId());
            final List<EventShortDto> shortDtoList = EventMapper.toShortDtoList(eventsCustom);
            final CompilationDto compilationDto = CompilationMapper.toDto(compilation);
            compilationDto.setEvents(shortDtoList);
            result.add(compilationDto);
        }
        return result;
    }

    @Override
    public CompilationDto getCompilation(Integer id) {
        final Compilation compInDb = compilationRepository.findById(id).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", id)));
        final List<EventWithRequestsViews> eventsCustom = eventRepository
                .findByCompilationIdWithRequestsViews(id);
        final List<EventShortDto> shortDtoList = EventMapper.toShortDtoList(eventsCustom);
        final CompilationDto compilationDto = CompilationMapper.toDto(compInDb);
        compilationDto.setEvents(shortDtoList);
        return compilationDto;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newDto) {
        final CompilationDto dto = CompilationDto.builder()
                .title(newDto.getTitle())
                .pinned(newDto.isPinned())
                .build();
        final Compilation compilation = CompilationMapper.toCompilation(dto);
        final List<Event> events = eventRepository.findAllById(newDto.getEvents());
        compilation.setEvents(new HashSet<>(events));
        final Compilation savedCompilation = compilationRepository.save(compilation);
        log.info("Compilation {} created", savedCompilation.getId());
        final List<EventWithRequestsViews> eventsCustom = eventRepository
                .findByEventIdWithRequestsViews(newDto.getEvents());
        final List<EventShortDto> shortDtoList = EventMapper.toShortDtoList(eventsCustom);
        final CompilationDto compilationDto = CompilationMapper.toDto(savedCompilation);
        compilationDto.setEvents(shortDtoList);
        return compilationDto;
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
        log.info("Event {} removed from compilation {}", eventId, savedCompilation.getId());
    }

    @Override
    public void addEventCompilation(Integer compId, Event newEvent) {
        final Compilation compInDb = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", compId)));
        compInDb.getEvents().add(newEvent);
        final Compilation savedCompilation = compilationRepository.save(compInDb);
        log.info("Event {} removed from compilation {}", newEvent, savedCompilation.getId());
    }

    @Override
    public void unPinCompilation(Integer compId) {
        changePinned(compId, false);
    }

    @Override
    public void pinCompilation(Integer compId) {
        changePinned(compId, true);
    }

    private void changePinned(Integer compId, boolean pinned) {
        final Compilation compInDb = compilationRepository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found.", compId)));
        compInDb.setPinned(pinned);
        final Compilation savedCompilation = compilationRepository.save(compInDb);
        log.info("Compilation {} {}", savedCompilation.getId(), savedCompilation.getPinned());
    }
}
