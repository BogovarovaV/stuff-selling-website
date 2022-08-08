package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.Users;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User usersEntityToUserDto(Users users);

    List<User> usersEntitiesToUserDtos(List<Users> usersList);
}
