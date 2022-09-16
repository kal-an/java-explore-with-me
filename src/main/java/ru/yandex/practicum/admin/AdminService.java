package ru.yandex.practicum.admin;

import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.event.dto.AdminUpdateEventRequest;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.user.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<EventFullDto> getAllEvents(List<Integer> users,
                                    List<String> states, List<Integer> categories,
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
