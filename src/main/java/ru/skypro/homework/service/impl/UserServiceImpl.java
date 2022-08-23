package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Get a list of all users
     * @return list of all users as ResponseWrapperUser (DTO)
     */
    @Override
    public ResponseWrapperUser getAllUsers() {
        List<User> userDtoList = userMapper.usersEntitiesToUserDtos(userRepository.findAllUsers());
        ResponseWrapperUser responseWrapperUser = new ResponseWrapperUser();
        responseWrapperUser.setCount(userDtoList.size());
        responseWrapperUser.setResults(userDtoList);
        return responseWrapperUser;
    }

    /**
     * Update user by username
     * @param userDto - user information
     * @param username - username
     * @return updated user as User (DTO)
     */
    @Override
    public User updateUser(User userDto, String username) {
        Users users = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setPhone(userDto.getPhone());
        userRepository.save(users);
        return userDto;
    }

    /**
     * Get user by ID
     * @param id - user ID from client
     * @return found user as User (DTO)
     */
    @Override
    public User getUser(Integer id) {
        Users users = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.usersEntityToUserDto(users);
    }

    /**
     * Get user by username
     * @param username - username from client
     * @return found user as Users (model)
     */
    @Override
    public Users getUserByUsername(String username) {
        Users users = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        return users;
    }

    /**
     * Update user
     * @param users - user information from client
     * @return updated user as Users (model)
     */
    @Override
    public Users updateUser(Users users) {
        Users user = userRepository.findUsersByUsername(users.getUsername()).orElseThrow(UserNotFoundException::new);
        user.setFirstName(users.getFirstName());
        user.setLastName(users.getLastName());
        user.setPhone(users.getPhone());
        userRepository.save(users);
        logger.info("User {} has been registered", user.getFirstName());
        return user;
    }

    /**
     * Save new password
     * @param username - username from client
     * @param newPassword - new password from client
     */
    @Override
    public void savePassword(String username, String newPassword) {
        Users users = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        users.setPassword(newPassword);
        userRepository.save(users);
    }
}