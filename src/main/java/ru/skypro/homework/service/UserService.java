package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.ResponseWrapperUserTo;
import ru.skypro.homework.dto.UserTo;
import ru.skypro.homework.model.User;

public interface UserService {

    ResponseWrapperUserTo getAllUsersWithOrderById();

    UserTo updateUser(UserTo userDto, Authentication authentication);

    UserTo getUser(Integer id);

    User getUserByUsername(String username);

    User updateUser(User user);

    void savePassword(String username, String newPassword);
}
