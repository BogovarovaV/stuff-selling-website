package ru.skypro.homework.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.controller.AdsAvatarController;
import ru.skypro.homework.model.AdsAvatar;
import ru.skypro.homework.repository.AdsAvatarRepository;
import ru.skypro.homework.service.impl.AdsAvatarServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.homework.DataTest.ADS_AVATAR_ID;
import static ru.skypro.homework.DataTest.ADS_AVATAR_IMAGE;

@WebMvcTest(controllers = AdsAvatarController.class)
public class AdsAvatarControllerTest {

    private AdsAvatar adsAvatar;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private AdsAvatarRepository adsAvatarRepository;

    @SpyBean
    private AdsAvatarServiceImpl adsAvatarService;

    @InjectMocks
    private AdsAvatarController adsAvatarController;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        adsAvatar = new AdsAvatar(ADS_AVATAR_ID, ADS_AVATAR_IMAGE);
    }

    @Test
    public void testShouldUploadImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE, ADS_AVATAR_IMAGE);
        when(adsAvatarRepository.saveAndFlush(any())).thenReturn(adsAvatar);
        mockMvc.perform(multipart("/upl").file(image))
                .andExpect(status().isOk());
    }

    @Test
    public void testShouldGetImage() throws Exception {
        when(adsAvatarRepository.findById(any())).thenReturn(Optional.ofNullable(adsAvatar));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/1/image")
                )
                .andExpect(status().isOk());
    }
}
