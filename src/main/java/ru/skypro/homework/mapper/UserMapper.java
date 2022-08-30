package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.UserTo;
import ru.skypro.homework.model.User;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserTo usersEntityToUserDto(User user);

    List<UserTo> usersEntitiesToUserDtos(List<User> userList);
}
