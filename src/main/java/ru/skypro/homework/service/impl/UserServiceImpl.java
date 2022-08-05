package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(CreateUser createUserDto) {
        Users createdUser = convertCreateUserDtoToUsersEntity(createUserDto);
        userRepository.save(createdUser);
    }

    @Override
    public ResponseWrapperUser getAllUsers() {
        return convertUsersEntityToResponseWrapperUserDto(userRepository.findAll());
    }

    @Override
    public User updateUser(User userDto) {
        if (isUserExists(userDto.getId())) {
            userRepository.save(convertUserDtoToUsersEntity(userDto));
            return userDto;
        }
        return null;
    }

    @Override
    public User getUser(Integer id) {
        if (isUserExists(id)) {
            return convertUsersEntityToUserDto(userRepository.findById(id).get());
        }
        return null;
    }

    @Override
    public User convertUsersEntityToUserDto(Users users) {
        User user = new User();
        user.setId(users.getId());
        user.setFirstName(users.getFirstName());
        user.setLastName(users.getLastName());
        user.setEmail(users.getEmail());
        user.setPhone(users.getPhone());
        return user;
    }

    @Override
    public Users convertCreateUserDtoToUsersEntity(CreateUser createUserDto) {
        Users users = new Users();
        users.setEmail(createUserDto.getEmail());
        users.setFirstName(createUserDto.getFirstName());
        users.setLastName(createUserDto.getLastName());
        users.setPassword(createUserDto.getPassword());
        users.setPhone(createUserDto.getPhone());
        users.setRoles(Users.Roles.USER);
        users.setUsername(generateUsername(users.getFirstName(), users.getLastName()));
        return users;
    }

    @Override
    public ResponseWrapperUser convertUsersEntityToResponseWrapperUserDto(List<Users> allUsers) {
        ResponseWrapperUser responseWrapperUser = new ResponseWrapperUser();
        List<User> list = new ArrayList<>(allUsers.size());
        for (Users users : allUsers) {
            list.add(convertUsersEntityToUserDto(users));
        }
        responseWrapperUser.setCount(list.size());
        responseWrapperUser.setResults(list);
        return responseWrapperUser;
    }

    @Override
    public Users convertUserDtoToUsersEntity(User userDto) {
        Users editedUsers = userRepository.getById(userDto.getId());
        editedUsers.setEmail(userDto.getEmail());
        editedUsers.setFirstName(userDto.getFirstName());
        editedUsers.setLastName(userDto.getLastName());
        editedUsers.setPhone(userDto.getPhone());
        return editedUsers;
    }

    private String generateUsername(String firstName, String lastName) {
        UUID randomUUID = UUID.randomUUID();
        String randomString = randomUUID.toString().replaceAll("-", "");
        String username = lastName.substring(0, (lastName.length() - 1))
                + firstName.charAt(0) + randomString.substring(0, 5);
        return username;
    }

    private boolean isUserExists(Integer id) {
        return userRepository.findById(id).isPresent();
    }
}
