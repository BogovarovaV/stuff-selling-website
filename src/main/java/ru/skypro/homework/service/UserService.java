package ru.skypro.homework.service;

import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.Users;

import java.util.List;

public interface UserService {

    void createUser(CreateUser createUserDto);

    ResponseWrapperUser getAllUsers();

    User updateUser(User user);

    User getUser(Integer id);

    User convertUsersEntityToUserDto(Users users);

    Users convertCreateUserDtoToUsersEntity(CreateUser createUserDto);

    ResponseWrapperUser convertUsersEntityToResponseWrapperUserDto(List<Users> allUser);

    Users convertUserDtoToUsersEntity(User userDto);
}
