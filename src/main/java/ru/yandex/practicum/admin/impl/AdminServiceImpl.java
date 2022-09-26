package ru.yandex.practicum.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.AdminRepository;
import ru.yandex.practicum.admin.AdminService;
import ru.yandex.practicum.category.CategoryService;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.compilation.CompilationService;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.event.EventMapper;
import ru.yandex.practicum.event.EventService;
import ru.yandex.practicum.event.dto.AdminUpdateEventRequest;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.State;
import ru.yandex.practicum.user.UserMapper;
import ru.yandex.practicum.user.UserNotFoundException;
import ru.yandex.practicum.user.dto.UserDto;
import ru.yandex.practicum.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final EventService eventService;
    private final CategoryService categoryService;
    private final AdminRepository adminRepository;
    private final CompilationService compilationService;

    public AdminServiceImpl(EventService eventService,
                            CategoryService categoryService,
                            AdminRepository adminRepository,
                            CompilationService compilationService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.adminRepository = adminRepository;
        this.compilationService = compilationService;
    }

    @Override
    public List<EventFullDto> getAllEvents(List<Integer> users,
                                           List<State> states, List<Integer> categories,
                                           String rangeStart, String rangeEnd,
                                           Integer from, Integer size) {
        return eventService.getAllEvents(users, states, categories,
                rangeStart, rangeEnd, from, size);
    }

    @Override
    public EventFullDto updateEvent(Integer eventId,
                                    AdminUpdateEventRequest updateDto) {
        return eventService.updateEvent(eventId, updateDto);
    }

    @Override
    public EventFullDto publishEvent(Integer eventId) {
        return eventService.publishEvent(eventId);
    }

    @Override
    public EventFullDto rejectEvent(Integer eventId) {
        return eventService.rejectEvent(eventId);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto updateDto) {
        return categoryService.updateCategory(updateDto);
    }

    @Override
    public CategoryDto createCategory(CategoryDto newDto) {
        return categoryService.createCategory(newDto);
    }

    @Override
    public void deleteCategory(Integer catId) {
        categoryService.deleteCategory(catId);
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

    @Override
    public List<UserDto> getUsers(List<Integer> ids,
                                  Integer from, Integer size) {
        if (!ids.isEmpty()) {
            return adminRepository.findAllById(ids).stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            int page = from / size;
            Pageable pageable = PageRequest.of(page, size);
            return adminRepository.findAll(pageable).stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public UserDto addUser(UserDto newDto) {
        final User user = UserMapper.toUser(newDto);
        final User savedUser = adminRepository.save(user);
        log.info("User {} saved", savedUser);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        final User user = adminRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        adminRepository.delete(user);
    }

}
