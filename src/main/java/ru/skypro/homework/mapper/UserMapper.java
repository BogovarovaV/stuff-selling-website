package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto usersEntityToUserDto(User user);

    List<UserDto> usersEntitiesToUserDtos(List<User> userList);
}
