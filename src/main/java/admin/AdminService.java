package admin;

import category.dto.CategoryDto;
import compilation.dto.CompilationDto;
import compilation.dto.NewCompilationDto;
import event.dto.AdminUpdateEventRequest;
import event.dto.EventFullDto;
import user.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<EventFullDto> getAllEvents(Integer authUser, List<Integer> users,
                                    List<String> states, List<Integer> categories,
                                    String rangeStart, String rangeEnd,
                                    Integer from, Integer size);

    EventFullDto updateEvent(Integer authUser, Integer eventId, AdminUpdateEventRequest updateDto);

    EventFullDto publishEvent(Integer authUser, Integer eventId);

    EventFullDto rejectEvent(Integer authUser, Integer eventId);

    CategoryDto updateCategory(Integer authUser, CategoryDto updateDto);

    CategoryDto createCategory(Integer authUser, CategoryDto newDto);

    void deleteCategory(Integer authUser, Integer catId);

    CompilationDto createCompilation(Integer authUser, NewCompilationDto newDto);

    void deleteCompilation(Integer authUser, Integer compId);

    void deleteEventCompilation(Integer authUser, Integer compId, Integer eventId);

    void addEventCompilation(Integer authUser, Integer compId, Integer eventId);

    void unPinCompilation(Integer authUser, Integer compId);

    void pinCompilation(Integer authUser, Integer compId);

    List<UserDto> getUsers(Integer authUser, List<Integer> ids, Integer from, Integer size);

    UserDto addUser(Integer authUser, UserDto newDto);

    void deleteUser(Integer authUser, Integer userId);
}
