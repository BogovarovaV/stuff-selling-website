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
import org.springframework.security.test.context.support.WithAnonymousUser;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.homework.DataTest.*;
import static org.mockito.Mockito.*;


@WebMvcTest(controllers = AdsController.class)
public class AdsControllerTest {

    private AdsTo adsTo;
    private Advert advert;
    private CreateAdsTo createAdsTo;
    private FullAdsTo fullAds;
    private UserTo userTo;
    private User user;
    private AdsAvatar adsAvatar;
    private JSONObject advertObject;
    private JSONObject commentObject;
    private MockMultipartFile adsJson;
    private MockMultipartFile image;
    private Comment comment;
    private AdsCommentTo commentTo;
    private List<AdsCommentTo> commentToList;
    private List<AdsTo> adsToList;
    private List<Advert> advertList;

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
        adsTo.setPrice(PRICE);
        adsTo.setTitle(TITLE);
        adsTo.setImage(IMAGE);
        adsTo.setAuthor(USER_ID);
        adsTo.setDescription(DESC);

        adsToList = new ArrayList<>();
        adsToList.add(adsTo);
        userTo = new UserTo();
        userTo.setId(USER_ID);
        userTo.setFirstName(FIRSTNAME);
        userTo.setLastName(LASTNAME);
        userTo.setEmail(EMAIL);
        userTo.setPhone(PHONE);
        createAdsTo = new CreateAdsTo();
        createAdsTo.setDescription(DESC);

        createAdsTo.setPrice(PRICE);
        createAdsTo.setTitle(TITLE);

        fullAds = new FullAdsTo();
        fullAds.setAuthorFirstName(FIRSTNAME);
        fullAds.setAuthorLastName(LASTNAME);
        fullAds.setDescription(DESC);
        fullAds.setEmail(EMAIL);
        fullAds.setImage(IMAGE);
        fullAds.setPhone(PHONE);
        fullAds.setPk(ADS_ID);
        fullAds.setPrice(PRICE);
        fullAds.setTitle(TITLE);

        commentTo = new AdsCommentTo();
        commentTo.setAuthor(USER_ID);
        commentTo.setCreatedAt(DATE_TIME);
        commentTo.setPk(COMMENT_ID);
        commentTo.setText(TEXT_1);

        commentToList = new ArrayList<>();
        commentToList.add(commentTo);

        //entity

        advert = new Advert();
        advert.setId(ADS_ID);
        advert.setPrice(PRICE);
        advert.setTitle(TITLE);
        advert.setImage(IMAGE);
        advert.setDescription(DESC);

        advertList = new ArrayList<>();
        advertList.add(advert);

        comment = new Comment();
        comment.setId(COMMENT_ID);
        comment.setText(TEXT_1);
        comment.setAds(advert);
        comment.setCreatedAt(DATE_TIME);

        user = new User(USER_ID, FIRSTNAME, LASTNAME, EMAIL,
                PHONE, USERNAME, PASSWORD, true, List.of(advert), List.of(comment));

        advert.setUser(user);
        comment.setUser(user);

        adsAvatar = new AdsAvatar();
        adsAvatar.setId(ADS_AVATAR_ID);
        adsAvatar.setImage(ADS_AVATAR_IMAGE);

        //JSON

        adsJson = new MockMultipartFile(
                "properties",
                "",
                "application/json",
                "{\"description\": \"description1\", \"price\": \"10000\",\"title\": \"title1\"}".getBytes());

        image = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE, ADS_AVATAR_IMAGE);

        advertObject = new JSONObject();
        advertObject.put("author", USER_ID);
        advertObject.put("image", IMAGE);
        advertObject.put("pk", ADS_ID);
        advertObject.put("price", PRICE_2);
        advertObject.put("title", TITLE);
        advertObject.put("description", DESC);

        commentObject = new JSONObject();
        commentObject.put("author", USER_ID);
        commentObject.put("createdAt", DATE_TIME.toString());
        commentObject.put("pk", COMMENT_ID);
        commentObject.put("text", TEXT_2);
    }

    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Test
    public void testShouldGetAllAds() throws Exception {
        when(adsMapper.advertEntitiesToAdsDtos(any(List.class))).thenReturn((List.of(adsTo)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.count").value(COUNT))
                        .andExpect(jsonPath("$.results[*].author").value(USER_ID))
                        .andExpect(jsonPath("$.results[*].image").value(IMAGE))
                        .andExpect(jsonPath("$.results[*].pk").value(ADS_ID))
                        .andExpect(jsonPath("$.results[*].price").value(PRICE))
                        .andExpect(jsonPath("$.results[*].title").value(TITLE))
                        .andExpect(jsonPath("$.results[*].description").value(DESC));

    }

    @WithMockUser(username = USERNAME, authorities = "USER")
    @Test
    public void testShouldCreateAds() throws Exception {
        when(auth.getName()).thenReturn(USERNAME);
        when(adsMapper.createAdsDtoToAdvertEntity(any())).thenReturn(advert);
        when(userRepository.findUsersByUsername(any())).thenReturn(Optional.ofNullable(user));
        when(adsMapper.advertEntityToAdsDto(any())).thenReturn(adsTo);

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/ads")
                        .file(adsJson)
                        .file(image))
                        .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = "USER")
    @Test
    public void testShouldFindOneAdsBySearchOrAll() throws Exception {
        when(adsMapper.advertEntitiesToAdsDtos(any())).thenReturn(adsToList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads"))
                        .andExpect(status().isOk());
    }

    @WithMockUser(username = USERNAME, password = PASSWORD, authorities = "USER")
    @Test
    public void testShouldRemoveAds() throws Exception {
        when(auth.getName()).thenReturn(USERNAME);
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));
        doNothing().when(adsAvatarRepository).delete(any());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/"+ USER_ID))
                        .andExpect(status().isOk());
    }

    @WithMockUser(username = USERNAME, password = PASSWORD, authorities = "USER")
    @Test
    public void testShouldGetAdsById() throws Exception {
        when(auth.getName()).thenReturn(USERNAME);
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));
        when(adsMapper.advertEntityToFullAdsDto(any())).thenReturn(fullAds);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/" + ADS_ID))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.authorFirstName").value(FIRSTNAME))
                        .andExpect(jsonPath("$.authorLastName").value(LASTNAME))
                        .andExpect(jsonPath("$.description").value(DESC))
                        .andExpect(jsonPath("$.email").value(EMAIL))
                        .andExpect(jsonPath("$.phone").value(PHONE))
                        .andExpect(jsonPath("$.pk").value(ADS_ID))
                        .andExpect(jsonPath("$.price").value(PRICE))
                        .andExpect(jsonPath("$.title").value(TITLE));
    }

    @WithMockUser(username = "user", authorities = "USER")
    @Test
    public void testShouldGetAdsComments() throws Exception {
        when(commentMapper.commentEntitiesToAdsCommentDtos(any())).thenReturn(commentToList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/" + ADS_ID + "/comments"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.count").value(COUNT))
                        .andExpect(jsonPath(("$.results")).hasJsonPath())
                        .andExpect(jsonPath("$.results[*].author").value(USER_ID))
                        .andExpect(jsonPath("$.results[*].pk").value(COMMENT_ID))
                        .andExpect(jsonPath("$.results[*].text").value(TEXT_1));
    }

    @WithMockUser(username = USERNAME, password = PASSWORD, authorities = "USER")
    @Test
    public void testShouldAddAdsComments() throws Exception {
        when(auth.getName()).thenReturn(USERNAME);
        when(commentMapper.adsCommentDtoToCommentEntity(any())).thenReturn(comment);
        when(userRepository.findUsersByUsername(any())).thenReturn(Optional.ofNullable(user));
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ads/" + ADS_ID + "/comments")

                        .content(commentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.author").value(USER_ID));
    }

    @WithMockUser(username = USERNAME, password = PASSWORD, authorities = "USER")
    @Test
    public void testShouldDeleteAdsComment() throws Exception {
        when(auth.getName()).thenReturn(USERNAME);
        when(commentRepository.findCommentById(any())).thenReturn(Optional.of(comment));
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));
        when(userRepository.getById(any())).thenReturn(user);
        doNothing().when(commentRepository).delete(any());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/" + ADS_ID + "/comments/" + COMMENT_ID)
                )
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "stranger@mail.ru", authorities = "USER")
    @Test
    public void testShouldThrowException_whenUserTryingToDeleteAlienAdsCommentsById() throws Exception {
        when(commentRepository.findCommentById(any())).thenReturn(Optional.ofNullable(comment));
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));
        when(userRepository.getById(any())).thenReturn(user);
        when(auth.getName()).thenReturn("stranger@mail.ru");
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/1/comments/1")
                )
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", authorities = "USER")
    @Test
    public void testShouldGetAdsCommentsById() throws Exception {
        when(commentRepository.findCommentById(any())).thenReturn(Optional.of(comment));
        when(commentMapper.commentEntityToAdsCommentDto(any())).thenReturn(commentTo);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/" + ADS_ID + "/comments/" + COMMENT_ID)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.author").value(USER_ID))
                        .andExpect(jsonPath("$.pk").value(COMMENT_ID))
                        .andExpect(jsonPath("$.text").value(TEXT_1));
    }

    @WithMockUser(username = USERNAME, authorities = "USER")
    @Test
    public void testShouldUpdateAdsCommentsById() throws Exception {
        when(auth.getName()).thenReturn(USERNAME);
        when(commentRepository.findCommentById(any())).thenReturn(Optional.ofNullable(comment));
        comment.setText(TEXT_2);
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));
        when(userRepository.getById(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/" + ADS_ID + "/comments/" + COMMENT_ID)
                        .content(commentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.author").value(USER_ID))
                        .andExpect(jsonPath("$.pk").value(COMMENT_ID))
                        .andExpect(jsonPath("$.text").value(TEXT_2));
    }

    @WithMockUser(username = "stranger@mail.ru", authorities = "USER")
    @Test
    public void testShouldThrowException_whenUserTryingToUpdateAlienAdsCommentsById() throws Exception {
        comment.setText(TEXT_2);
        when(commentRepository.findCommentById(any())).thenReturn(Optional.ofNullable(comment));
        when(advertRepository.findById(any())).thenReturn(Optional.ofNullable(advert));
        when(userRepository.getById(any())).thenReturn(user);
        when(auth.getName()).thenReturn("stranger@mail.ru");
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1/comments/1")
                        .content(commentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }


}
