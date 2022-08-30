package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

public interface UserService {

    ResponseWrapperUser getAllUsersWithOrderById();

    UserDto updateUser(UserDto userDto, Authentication authentication);

    UserDto getUser(Integer id);

    User getUserByUsername(String username);

    User updateUser(User user);

    void savePassword(String username, String newPassword);
}
