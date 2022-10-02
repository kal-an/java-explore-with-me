package ru.yandex.practicum.explore.admin.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.explore.admin.AdminRepository;
import ru.yandex.practicum.explore.admin.AdminService;
import ru.yandex.practicum.explore.category.CategoryService;
import ru.yandex.practicum.explore.category.dto.CategoryDto;
import ru.yandex.practicum.explore.compilation.CompilationService;
import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.explore.event.EventMapper;
import ru.yandex.practicum.explore.event.EventService;
import ru.yandex.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.model.Event;
import ru.yandex.practicum.explore.event.model.State;
import ru.yandex.practicum.explore.user.UserMapper;
import ru.yandex.practicum.explore.user.UserNotFoundException;
import ru.yandex.practicum.explore.user.dto.UserDto;
import ru.yandex.practicum.explore.user.model.User;

import java.util.List;

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
        return eventService.updateEventByAdmin(eventId, updateDto);
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
            return UserMapper.toDtoList(adminRepository.findAllById(ids));
        } else {
            int page = from / size;
            Pageable pageable = PageRequest.of(page, size);
            return UserMapper.toDtoList(adminRepository.findAll(pageable));
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
