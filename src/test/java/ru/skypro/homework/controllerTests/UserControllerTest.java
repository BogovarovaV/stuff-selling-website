package ru.skypro.homework.controllerTests;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.UserTo;

import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.*;
import ru.skypro.homework.service.impl.AuthServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.homework.DataTest.*;

@WebMvcTest
class UserControllerTest {

    private User user;
    private UserTo userTo;
    private JSONObject userObject;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private Authentication auth;

    @MockBean
    private AdsAvatarService adsAvatarService;

    @MockBean
    private AdvertService advertService;

    @MockBean
    private CommentService commentService;

    @SpyBean
    private UserServiceImpl userService;

    @SpyBean
    private AuthServiceImpl authService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //entity
        user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        user.setFirstName(FIRSTNAME);
        user.setLastName(LASTNAME);
        user.setEmail(EMAIL);
        user.setPhone(PHONE);

        //dto
        userTo = new UserTo();
        userTo.setId(USER_ID);
        userTo.setFirstName(FIRSTNAME);
        userTo.setLastName(LASTNAME);

        userTo.setEmail(EMAIL);
        userTo.setPhone(PHONE);

        //Json
        userObject = new JSONObject();
        userObject.put("id", USER_ID);
        userObject.put("firstName", FIRSTNAME);
        userObject.put("lastName", LASTNAME);
        userObject.put("email", EMAIL);
        userObject.put("phone", PHONE_2);
    }

    @WithMockUser(username="admin", authorities = "ADMIN")
    @Test
    void shouldGetCurrentUserByUsername() throws Exception {
        when(userRepository.findUsersByUsername(any())).thenReturn(Optional.ofNullable(user));
        when(auth.getName()).thenReturn(USERNAME);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/me")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.firstName").value(FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(LASTNAME))
                .andExpect(jsonPath("$.email").value(EMAIL))

                .andExpect(jsonPath("$.phone").value(PHONE));
    }

    @WithMockUser(username=USERNAME, authorities = "USER")
    @Test
    void shouldUpdateUser() throws Exception {
        when(auth.getName()).thenReturn(USERNAME);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        user.setPhone(PHONE_2);


        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/me")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.firstName").value(FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(LASTNAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.phone").value(PHONE_2));
    }

    @WithMockUser(username="admin", authorities = "ADMIN")
    @Test
    void shouldGetAnyUserById() throws Exception {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(userMapper.usersEntityToUserDto(any())).thenReturn(userTo);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/" + USER_ID)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.firstName").value(FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(LASTNAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.phone").value(PHONE));
    }

    @WithMockUser(username="admin", authorities = "ADMIN")
    @Test
    void shouldGetAllUsersWithOrderById() throws Exception {
        when(userMapper.usersEntitiesToUserDtos(any())).thenReturn(List.of(userTo));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(COUNT))
                .andExpect(jsonPath("$.results[*].id").value(USER_ID))
                .andExpect(jsonPath("$.results[*].firstName").value(FIRSTNAME))
                .andExpect(jsonPath("$.results[*].lastName").value(LASTNAME))
                .andExpect(jsonPath("$.results[*].email").value(EMAIL))
                .andExpect(jsonPath("$.results[*].phone").value(PHONE));
    }
}