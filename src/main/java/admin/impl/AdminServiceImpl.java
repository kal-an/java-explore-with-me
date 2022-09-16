package admin.impl;

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
import user.UserRepository;
import user.dto.UserDto;
import user.model.User;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final EventService eventService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final CompilationService compilationService;

    public AdminServiceImpl(EventService eventService,
                            UserRepository userRepository,
                            CompilationService compilationService,
                            CategoryService categoryService) {
        this.eventService = eventService;
        this.userRepository = userRepository;
        this.compilationService = compilationService;
        this.categoryService = categoryService;
    }

    @Override
    public List<EventFullDto> getAllEvents(Integer authUser, List<Integer> users,
                                           List<String> states, List<Integer> categories,
                                           String rangeStart, String rangeEnd,
                                           Integer from, Integer size) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return eventService.getAllEvents(users, states, categories,
                rangeStart, rangeEnd, from, size);
    }

    @Override
    public EventFullDto updateEvent(Integer authUser, Integer eventId,
                                    AdminUpdateEventRequest updateDto) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return eventService.updateEvent(eventId, updateDto);
    }

    @Override
    public EventFullDto publishEvent(Integer authUser, Integer eventId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return eventService.publishEvent(eventId);
    }

    @Override
    public EventFullDto rejectEvent(Integer authUser, Integer eventId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return eventService.rejectEvent(eventId);
    }

    @Override
    public CategoryDto updateCategory(Integer authUser, CategoryDto updateDto) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return categoryService.updateCategory(updateDto);
    }

    @Override
    public CategoryDto createCategory(Integer authUser, CategoryDto newDto) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return categoryService.createCategory(newDto);
    }

    @Override
    public void deleteCategory(Integer authUser, Integer catId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        categoryService.deleteCategory(catId);
    }

    @Override
    public CompilationDto createCompilation(Integer authUser, NewCompilationDto newDto) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        return compilationService.createCompilation(newDto);
    }

    @Override
    public void deleteCompilation(Integer authUser, Integer compId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        compilationService.deleteCompilation(compId);
    }

    @Override
    public void deleteEventCompilation(Integer authUser, Integer compId, Integer eventId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        eventService.getEvent(eventId);
        compilationService.deleteEventCompilation(compId, eventId);
    }

    @Override
    public void addEventCompilation(Integer authUser, Integer compId, Integer eventId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        final Event newEvent = EventMapper.toEvent(eventService.getEvent(eventId));
        compilationService.addEventCompilation(compId, newEvent);
    }

    @Override
    public void unPinCompilation(Integer authUser, Integer compId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        compilationService.unPinCompilation(compId);
    }

    @Override
    public void pinCompilation(Integer authUser, Integer compId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        compilationService.pinCompilation(compId);
    }

    @Override
    public List<UserDto> getUsers(Integer authUser, List<Integer> ids,
                                  @Min(0) Integer from, @Min(1) Integer size) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        if (!ids.isEmpty()) {
            return userRepository.findAllById(ids).stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            int page = from / size;
            Pageable pageable = PageRequest.of(page, size);
            return userRepository.findAll(pageable).stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public UserDto addUser(Integer authUser, UserDto newDto) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        final User user = UserMapper.toUser(newDto);
        final User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser(Integer authUser, Integer userId) {
        userRepository.findById(authUser).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", authUser)));
        final User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String
                        .format("User with id=%d was not found.", userId)));
        userRepository.delete(user);
    }

}
