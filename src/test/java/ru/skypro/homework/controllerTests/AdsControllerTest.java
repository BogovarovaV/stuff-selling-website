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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.AdsAvatar;
import ru.skypro.homework.model.Advert;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsAvatarRepository;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsAvatarService;
import ru.skypro.homework.service.impl.AdvertServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.homework.DataTest.*;

@WebMvcTest(controllers = AdsController.class)
public class AdsControllerTest {

    private AdsTo adsTo;
    private Advert advert;
    private CreateAdsTo createAdsTo;
    private UserTo userTo;
    private User user;
    private AdsAvatar adsAvatar;
    private JSONObject advertObject1;
    private JSONObject properties;
    private MockMultipartFile image;
    private Comment comment;
    private AdsCommentTo commentTo;
    private List<AdsCommentTo> commentToList;
    private JSONObject commentObject;


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private AdvertRepository advertRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AdsMapper adsMapper;
    @MockBean
    private AdsAvatarService adsAvatarService;
    @MockBean
    private AdsAvatarRepository adsAvatarRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private Authentication auth;

    @SpyBean
    private AdvertServiceImpl advertService;
    @SpyBean
    private CommentServiceImpl commentService;

    @InjectMocks
    AdsController adsController;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //dto
        adsTo = new AdsTo();
        adsTo.setPk(ADS_ID);
        adsTo.setPrice(PRICE_1);
        adsTo.setTitle(TITLE);
        adsTo.setImage(IMAGE);
        adsTo.setAuthor(USER_ID);
        adsTo.setDescription(DESC);

        userTo = new UserTo();
        userTo.setId(USER_ID);
        userTo.setFirstName(FIRSTNAME);
        userTo.setLastName(LASTNAME);
        userTo.setEmail(EMAIL_1);
        userTo.setPhone(PHONE);

        createAdsTo = new CreateAdsTo();
        createAdsTo.setDescription(DESC);
        createAdsTo.setPrice(PRICE_1);
        createAdsTo.setTitle(TITLE);

        FullAdsTo fullAds = new FullAdsTo();
        fullAds.setAuthorFirstName(FIRSTNAME);
        fullAds.setAuthorLastName(LASTNAME);
        fullAds.setDescription(DESC);
        fullAds.setEmail(EMAIL_1);
        fullAds.setImage(IMAGE);
        fullAds.setPhone(PHONE);
        fullAds.setPk(ADS_ID);
        fullAds.setPrice(PRICE_1);
        fullAds.setTitle(TITLE);

        commentTo = new AdsCommentTo();
        commentTo.setAuthor(USER_ID);
        commentTo.setCreatedAt(DATE_TIME_1);
        commentTo.setPk(COMMENT_ID_1);
        commentTo.setText(TEXT_1);

        commentToList = new ArrayList<>();
        commentToList.add(commentTo);

        //entity
        advert = new Advert();
        advert.setId(ADS_ID);
        advert.setPrice(PRICE_1);
        advert.setTitle(TITLE);
        advert.setImage(IMAGE);
        advert.setDescription(DESC);

        adsAvatar = new AdsAvatar();
        adsAvatar.setId(ADS_AVATAR_ID);
        adsAvatar.setImage(ADS_AVATAR_IMAGE);

        comment = new Comment();
        comment.setId(COMMENT_ID_1);
        comment.setText(TEXT_1);
        comment.setAds(advert);
        comment.setCreatedAt(DATE_TIME_1);

        user = new User(USER_ID, FIRSTNAME, LASTNAME, EMAIL_1,
                PHONE, USERNAME, PASSWORD, true, List.of(advert), List.of(comment));

        advert.setUser(user);
        comment.setUser(user);

        //JSON

        image = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE, ADS_AVATAR_IMAGE);

        properties = new JSONObject();
        properties.put("description", DESC);
        properties.put("price", PRICE_1);
        properties.put("title", TITLE);
        advertObject1 = new JSONObject();
        advertObject1.put("properties", properties);
        advertObject1.put("file", image);

        commentObject = new JSONObject();
        commentObject.put("author", USER_ID);
        commentObject.put("createdAt", DATE_TIME_1.toString());
        commentObject.put("pk", COMMENT_ID_1);
        commentObject.put("text", TEXT_2);


    }

    @WithMockUser(username = USERNAME, authorities = "USER")
    @Test
    public void testCreateAds() throws Exception {
        when(adsMapper.createAdsDtoToAdvertEntity(any())).thenReturn(advert);
        when(auth.getName()).thenReturn(USERNAME);
        when(userRepository.findUsersByUsername(any())).thenReturn(Optional.ofNullable(user));
        when(adsMapper.advertEntityToAdsDto(any())).thenReturn(adsTo);

        MockMultipartFile adsJson = new MockMultipartFile("properties", "",
                "application/json", "{\"description\": \"description1\", \"price\": \"10000\",\"title\": \"title1\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/ads")
                        .file(adsJson)
                        .file(image))

                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = "USER")
    @Test
    public void testShouldGetAdsAllComments() throws Exception {
        when(commentMapper.commentEntitiesToAdsCommentDtos(any())).thenReturn(commentToList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1/comments")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath(("$.results")).hasJsonPath())
                .andExpect(jsonPath("$.results[*].author").value(USER_ID))
                .andExpect(jsonPath("$.results[*].pk").value(COMMENT_ID_1))
                .andExpect(jsonPath("$.results[*].text").value(TEXT_1));
    }

    @WithMockUser(username = USERNAME, password = PASSWORD, authorities = "USER")
    @Test
    public void testShouldAddAdsComments() throws Exception {
        when(auth.getName()).thenReturn("uu@gmail.com");
        when(commentMapper.adsCommentDtoToCommentEntity(any())).thenReturn(comment);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ads/1/comments")
                        .content(commentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(USER_ID));

    }

    @WithMockUser(username = "user", authorities = "USER")
    @Test
    public void testShouldGetAdsCommentsById() throws Exception {
        when(commentRepository.findCommentById(any())).thenReturn(Optional.of(comment));
        when(commentMapper.commentEntityToAdsCommentDto(any())).thenReturn(commentTo);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1/comments/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(USER_ID))
                .andExpect(jsonPath("$.pk").value(COMMENT_ID_1))
                .andExpect(jsonPath("$.text").value(TEXT_1));
    }

    @WithMockUser(username = "uu@gmail.com", authorities = "USER")
    @Test
    public void testShouldUpdateAdsCommentsById() throws Exception {
        comment.setText(TEXT_2);
        when(commentRepository.findCommentById(any())).thenReturn(Optional.ofNullable(comment));
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));
        when(userRepository.getById(any())).thenReturn(user);
        when(auth.getName()).thenReturn(USERNAME);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1/comments/1")
                        .content(commentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(USER_ID))
                .andExpect(jsonPath("$.pk").value(COMMENT_ID_1))
                .andExpect(jsonPath("$.text").value(TEXT_2));
    }

}
