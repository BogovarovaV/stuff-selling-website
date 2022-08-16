package ru.skypro.homework.service.impl;

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
    public User updateUser(User userDto) {
        Users users = userRepository.findById(userDto.getId()).orElseThrow(UserNotFoundException::new);
        users.setEmail(userDto.getEmail());
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setPhone(userDto.getPhone());
        users.setUsername(userDto.getEmail());
        userRepository.save(users);
        return userDto;
    }

    @Override
    public User getUser(Integer id) {
        Users users = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.usersEntityToUserDto(users);
    }
}
