package ru.skypro.homework.service;

import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;

public interface UserService {

    ResponseWrapperUser getAllUsers();

    User updateUser(User user);

    User getUser(Integer id);

}
