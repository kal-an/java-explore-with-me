package ru.yandex.practicum.explore.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.explore.admin.AdminCompilationService;
import ru.yandex.practicum.explore.compilation.CompilationService;
import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.explore.event.EventMapper;
import ru.yandex.practicum.explore.event.EventService;
import ru.yandex.practicum.explore.event.model.Event;

@Service
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final EventService eventService;
    private final CompilationService compilationService;

    public AdminCompilationServiceImpl(EventService eventService,
                                       CompilationService compilationService) {
        this.eventService = eventService;
        this.compilationService = compilationService;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newDto) {
        return compilationService.createCompilation(newDto);
    }

    @Override
    public void deleteCompilation(Integer compId) {
        compilationService.deleteCompilation(compId);
    }

    @Override
    public void deleteEventCompilation(Integer compId, Integer eventId) {
        eventService.getEvent(eventId);
        compilationService.deleteEventCompilation(compId, eventId);
    }

    @Override
    public void addEventCompilation(Integer compId, Integer eventId) {
        final Event newEvent = EventMapper.toEvent(eventService.getEvent(eventId));
        compilationService.addEventCompilation(compId, newEvent);
    }

    @Override
    public void unPinCompilation(Integer compId) {
        compilationService.unPinCompilation(compId);
    }

    @Override
    public void pinCompilation(Integer compId) {
        compilationService.pinCompilation(compId);
    }

}
