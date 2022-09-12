package user;

import user.dto.UserDto;
import user.dto.UserShortDto;
import user.model.User;

public class UserMapper {

    public static UserShortDto toShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static UserDto toDto(User user) {
        final UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(userDto.getName());
        userDto.setEmail(userDto.getEmail());
        return userDto;
    }

    public static User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}
