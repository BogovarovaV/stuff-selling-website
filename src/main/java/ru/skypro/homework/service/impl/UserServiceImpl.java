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


    @Override
    public ResponseWrapperUser getAllUsers() {
        List<User> userDtoList = userMapper.usersEntitiesToUserDtos(userRepository.findAllUsers());
        ResponseWrapperUser responseWrapperUser = new ResponseWrapperUser();
        responseWrapperUser.setCount(userDtoList.size());
        responseWrapperUser.setResults(userDtoList);
        return responseWrapperUser;
    }

    @Override
    public User updateUser(User userDto, String username) {
        Users users = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setPhone(userDto.getPhone());
        userRepository.save(users);
        return userDto;
    }

    @Override
    public User getUser(Integer id) {
        Users users = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.usersEntityToUserDto(users);
    }

    @Override
    public Users getUserByUsername(String username) {
        Users users = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        return users;
    }

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

    @Override
    public void savePassword(String username, String newPassword) {
        Users users = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        users.setPassword(newPassword);
        userRepository.save(users);
    }
}