package ru.skypro.homework.service;

import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

public interface UserService {

    ResponseWrapperUser getAllUsersWithOrderById();

    UserDto updateUser(UserDto userDto, String username);

    UserDto getUser(Integer id);

    User getUserByUsername(String username);

    User updateUser(User user);

    void savePassword(String username, String newPassword);
}
