package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ResponseWrapperUserTo;
import ru.skypro.homework.dto.UserTo;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
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
     * @return list of all users as ResponseWrapperUserTo (DTO)
     */
    @Override
    public ResponseWrapperUserTo getAllUsersWithOrderById() {
        List<UserTo> userDtoDtoList = userMapper.usersEntitiesToUserDtos(userRepository.findAllByOrderById());
        ResponseWrapperUserTo responseWrapperUserTo = new ResponseWrapperUserTo();
        responseWrapperUserTo.setCount(userDtoDtoList.size());
        responseWrapperUserTo.setResults(userDtoDtoList);
        return responseWrapperUserTo;
    }

    /**
     * Update user by username
     *
     * @param userDto        - user information
     * @param authentication - user's authentication
     * @return updated user as User (DTO)
     */
    @Override
    public UserTo updateUser(UserTo userDto, Authentication authentication) {
        String currentUsername = authentication.getName();
        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDto.getId()).orElseThrow(UserNotFoundException::new);
        // check if user has access to change data (has role "Admin" or user wants to change his own data)
        if (currentUserDetails.getAuthorities().toString().contains("ROLE_ADMIN")
                || currentUsername.equals(user.getUsername())) {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPhone(userDto.getPhone());
            userRepository.save(user);
            logger.info("User {} has been updated", user.getUsername());
        } else {
            throw new NoAccessException();
        }
        return userDto;
    }

    /**
     * Get user by ID
     * @param id - user ID from client
     * @return found user as User (DTO)
     */
    @Override
    public UserTo getUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.usersEntityToUserDto(user);
    }

    /**
     * Get user by username
     * @param username - username from client
     * @return found user as Users (model)
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Update user
     * @param users - user information from client
     * @return updated user as Users (model)
     */
    @Override
    public User updateUser(User users) {
        User user = userRepository.findUsersByUsername(users.getUsername()).orElseThrow(UserNotFoundException::new);
        user.setFirstName(users.getFirstName());
        user.setLastName(users.getLastName());
        user.setPhone(users.getPhone());
        userRepository.save(users);
        logger.info("User {} has been registered", user.getUsername());
        return user;
    }

    /**
     * Save new password
     * @param username - username from client
     * @param newPassword - new password from client
     */
    @Override
    public void savePassword(String username, String newPassword) {
        User user = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}