package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.skypro.homework.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private Users users;
    private User user;
    private ResponseWrapperUser responseUser;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserMapper userMapperMock;

    @InjectMocks
    private UserServiceImpl out;

    @BeforeEach
    public void setUp() {
        //entity
        users = new Users();
        users.setId(USER_ID);
        users.setFirstName(FIRSTNAME);
        users.setLastName(LASTNAME);
        users.setEmail(EMAIL_1);
        users.setPhone(PHONE);

        //dto
        user = new User();
        user.setId(USER_ID);
        user.setFirstName(FIRSTNAME);
        user.setLastName(LASTNAME);
        user.setEmail(EMAIL_1);
        user.setPhone(PHONE);

        responseUser = new ResponseWrapperUser();
        responseUser.setCount(1);
        responseUser.setResults(Arrays.asList(user));

        out = new UserServiceImpl(userRepositoryMock, userMapperMock);
    }

    @Test
    public void testShouldGetUserById() {
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(users));
        when(userMapperMock.usersEntityToUserDto(any(Users.class))).thenReturn(user);
        assertEquals(user, out.getUser(USER_ID));
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        when(userRepositoryMock.findById(any())).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> out.getUser(USER_ID));
    }

    @Test
    public void testShouldEditUser() {
       users.setFirstName(EMAIL_2);
       user.setLastName(EMAIL_2);
       when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(users));
       assertEquals(user, out.updateUser(user));
    }

    @Test
    public void testShouldGetAllUser() {
        when(userMapperMock.usersEntitiesToUserDtos(any(List.class))).thenReturn(Arrays.asList(user));
        assertEquals(responseUser, out.getAllUsers());
    }
}
