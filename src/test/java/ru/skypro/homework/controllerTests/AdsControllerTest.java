package ru.skypro.homework.controllerTests;

import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.AdsAvatar;
import ru.skypro.homework.model.Advert;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsAvatarRepository;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsAvatarService;
import ru.skypro.homework.service.impl.AdvertServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    MockMultipartFile file;

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
    public void setUp() throws JSONException {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();


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

        //entity
        Advert advert = new Advert();
        advert.setId(ADS_ID);
        advert.setPrice(PRICE_1);
        advert.setTitle(TITLE);
        advert.setImage(IMAGE);
        advert.setDescription(DESC);
        user = new User();
        user.setId(USER_ID);
        advert.setUser(user);


        adsAvatar = new AdsAvatar();
        adsAvatar.setId(ADS_AVATAR_ID);
        adsAvatar.setImage(ADS_AVATAR_IMAGE);

        //JSON

        file = new MockMultipartFile(
                "file",
                "image",
                MediaType.MULTIPART_FORM_DATA_VALUE, ADS_AVATAR_IMAGE);

        properties = new JSONObject();
        properties.put("descripton", DESC);
        properties.put("price", PRICE_1);
        properties.put("title", TITLE);
        advertObject1 = new JSONObject();
        advertObject1.put("properties", properties);
        advertObject1.put("file", file);







//        ResponseWrapperAdsTo responseWrapperAds = new ResponseWrapperAdsTo();
//        responseWrapperAds.setCount(COUNT);
//        responseWrapperAds.setResults(List.of(adsTo));
//
//        RegReqTo regReqTo = new RegReqTo();
//        regReqTo.setPassword(PASSWORD);
//        regReqTo.setUsername(USERNAME);
//        regReqTo.setRole(RegReqTo.RoleEnum.ADMIN);

    }


    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Test
    public void testGetAllAds() throws Exception {
        when(adsMapper.advertEntitiesToAdsDtos(any(List.class))).thenReturn((List.of(adsTo)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(COUNT))
                .andExpect(jsonPath("$.results[*].author").value(USER_ID))
                .andExpect(jsonPath("$.results[*].image").value(IMAGE))
                .andExpect(jsonPath("$.results[*].pk").value(ADS_ID))
                .andExpect(jsonPath("$.results[*].price").value(PRICE_1))
                .andExpect(jsonPath("$.results[*].title").value(TITLE))
                .andExpect(jsonPath("$.results[*].description").value(DESC));
    }

    @WithMockUser(username = "admin", authorities = "ADMIN")
    @Test
    public void testCreateAds() throws Exception {
        when(adsMapper.createAdsDtoToAdvertEntity(createAdsTo)).thenReturn(advert);
        when(userRepository.findUsersByUsername(any())).thenReturn(Optional.ofNullable(user));
        when(adsAvatarService.saveAds(file)).thenReturn("1");
        when(adsAvatarRepository.saveAndFlush(adsAvatar)).thenReturn(adsAvatar);
        when(advertRepository.save(any())).thenReturn(advert);

        mockMvc.perform(multipart("/api/1/image").file(file))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ads")
                        .content(String.valueOf(advertObject1))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

//
//    @Test
//    public void testGetAdsMe() {
//
//    }
//
//    @Test
//    public void testFindAds() {
//
//    }
//
//    @Test
//    public void testRemoveAds() {
//
//    }
//
//    @Test
//    public void testGetAds() {
//
//    }
//
//    @Test
//    public void testUpdateAds() {
//
//    }
//
//    @Test
//    public void testGetAdsComments() {
//
//    }
//
//    @Test
//    public void testAddAdsComment() {
//
//    }
//
//    @Test
//    public void testDeleteAdsComment() {
//
//    }
//
//    @Test
//    public void testGetAdsComment() {
//
//    }
//
//    @Test
//    public void testUpdateAdsComment() {
//
//    }

}
