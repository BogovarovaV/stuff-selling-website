package ru.skypro.homework.service;

import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.Users;

public interface UserService {

    ResponseWrapperUser getAllUsers();

    User updateUser(User userDto, String username);

    User getUser(Integer id);

    Users getUserByUsername(String username);

    Users updateUser(Users users);

    void savePassword(String username, String newPassword);
}
