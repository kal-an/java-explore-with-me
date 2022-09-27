package ru.yandex.practicum.explore.admin;

import ru.yandex.practicum.explore.category.dto.CategoryDto;
import ru.yandex.practicum.explore.compilation.dto.CompilationDto;
import ru.yandex.practicum.explore.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.yandex.practicum.explore.event.dto.EventFullDto;
import ru.yandex.practicum.explore.event.model.State;
import ru.yandex.practicum.explore.user.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<EventFullDto> getAllEvents(List<Integer> users,
                                    List<State> states, List<Integer> categories,
                                    String rangeStart, String rangeEnd,
                                    Integer from, Integer size);

    EventFullDto updateEvent(Integer eventId, AdminUpdateEventRequest updateDto);

    EventFullDto publishEvent(Integer eventId);

    EventFullDto rejectEvent(Integer eventId);

    CategoryDto updateCategory(CategoryDto updateDto);

    CategoryDto createCategory(CategoryDto newDto);

    void deleteCategory(Integer catId);

    CompilationDto createCompilation(NewCompilationDto newDto);

    void deleteCompilation(Integer compId);

    void deleteEventCompilation(Integer compId, Integer eventId);

    void addEventCompilation(Integer compId, Integer eventId);

    void unPinCompilation(Integer compId);

    void pinCompilation(Integer compId);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    UserDto addUser(UserDto newDto);

    void deleteUser(Integer userId);
}
