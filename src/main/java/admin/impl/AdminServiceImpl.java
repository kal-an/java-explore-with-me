package admin.impl;

import admin.AdminRepository;
import admin.AdminService;
import category.CategoryService;
import category.dto.CategoryDto;
import compilation.CompilationService;
import compilation.dto.CompilationDto;
import compilation.dto.NewCompilationDto;
import event.EventMapper;
import event.EventService;
import event.dto.AdminUpdateEventRequest;
import event.dto.EventFullDto;
import event.model.Event;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import user.UserMapper;
import user.UserNotFoundException;
import user.dto.UserDto;
import user.model.User;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public List<EventFullDto> getAllEvents(Integer authUser, List<Integer> users,
                                           List<String> states, List<Integer> categories,
                                           String rangeStart, String rangeEnd,
                                           Integer from, Integer size) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return eventService.getAllEvents(users, states, categories,
                rangeStart, rangeEnd, from, size);
    }

    @Override
    public EventFullDto updateEvent(Integer authUser, Integer eventId,
                                    AdminUpdateEventRequest updateDto) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return eventService.updateEvent(eventId, updateDto);
    }

    @Override
    public EventFullDto publishEvent(Integer authUser, Integer eventId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return eventService.publishEvent(eventId);
    }

    @Override
    public EventFullDto rejectEvent(Integer authUser, Integer eventId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return eventService.rejectEvent(eventId);
    }

    @Override
    public CategoryDto updateCategory(Integer authUser, CategoryDto updateDto) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return categoryService.updateCategory(updateDto);
    }

    @Override
    public CategoryDto createCategory(Integer authUser, CategoryDto newDto) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return categoryService.createCategory(newDto);
    }

    @Override
    public void deleteCategory(Integer authUser, Integer catId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        categoryService.deleteCategory(catId);
    }

    @Override
    public CompilationDto createCompilation(Integer authUser, NewCompilationDto newDto) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return compilationService.createCompilation(newDto);
    }

    @Override
    public void deleteCompilation(Integer authUser, Integer compId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        compilationService.deleteCompilation(compId);
    }

    @Override
    public void deleteEventCompilation(Integer authUser, Integer compId, Integer eventId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        eventService.getEvent(eventId);
        compilationService.deleteEventCompilation(compId, eventId);
    }

    @Override
    public void addEventCompilation(Integer authUser, Integer compId, Integer eventId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        final Event newEvent = EventMapper.toEvent(eventService.getEvent(eventId));
        compilationService.addEventCompilation(compId, newEvent);
    }

    @Override
    public void unPinCompilation(Integer authUser, Integer compId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        compilationService.unPinCompilation(compId);
    }

    @Override
    public void pinCompilation(Integer authUser, Integer compId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        compilationService.pinCompilation(compId);
    }

    @Override
    public List<UserDto> getUsers(Integer authUser, List<Integer> ids,
                                  @Min(0) Integer from, @Min(1) Integer size) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
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
    public UserDto addUser(Integer authUser, UserDto newDto) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        final User user = UserMapper.toUser(newDto);
        final User savedUser = adminRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser(Integer authUser, Integer userId) {
        adminRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        final User user = adminRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        adminRepository.delete(user);
    }

}
