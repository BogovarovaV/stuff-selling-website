package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.exception.AdvertNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Advert;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdvertServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class AdvertServiceTest {

    private Advert advert;
    private Ads ads;
    private ResponseWrapperAds responseAds;
    private Users users;
    private FullAds fullAds;
    private CreateAds createAds;

    @Mock
    private AdvertRepository advertRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private AdsMapper adsMapperMock;

    @InjectMocks
    private AdvertServiceImpl out;

    @BeforeEach
    public void setUp() {
        //entity
        advert = new Advert();
        advert.setId(ADS_ID);
        advert.setPrice(PRICE_1);
        advert.setTitle(TITLE);
        advert.setImage(IMAGE);
        advert.setDescription(DESC);
        users = new Users();
        users.setId(USER_ID);
        advert.setUsers(users);

        //dto
        ads = new Ads();
        ads.setPk(ADS_ID);
        ads.setPrice(PRICE_1);
        ads.setTitle(TITLE);
        ads.setImage(IMAGE);
        ads.setAuthor(USER_ID);

        responseAds = new ResponseWrapperAds();
        responseAds.setCount(1);
        responseAds.setResults(Arrays.asList(ads));

        fullAds = new FullAds();
        fullAds.setAuthorFirstName(FIRSTNAME);
        fullAds.setAuthorLastName(LASTNAME);
        fullAds.setDescription(DESC);
        fullAds.setEmail(EMAIL_1);
        fullAds.setImage(IMAGE);
        fullAds.setPhone(PHONE);
        fullAds.setPk(ADS_ID);
        fullAds.setPrice(PRICE_1);
        fullAds.setTitle(TITLE);

        createAds = new CreateAds();
        createAds.setDescription(DESC);
        createAds.setImage(IMAGE);
        createAds.setPk(ADS_ID);
        createAds.setPrice(PRICE_1);
        createAds.setTitle(TITLE);

        out = new AdvertServiceImpl(advertRepositoryMock, userRepositoryMock, adsMapperMock);
    }

    @Test
    public void testShouldGetAllAds() {
        when(adsMapperMock.advertEntitiesToAdsDtos(any(List.class))).thenReturn(Arrays.asList(ads));
        assertEquals(responseAds, out.getAllAds());
    }

    @Test
    public void testShouldCreateAds() {
        when(adsMapperMock.createAdsDtoToAdvertEntity(any(CreateAds.class))).thenReturn(advert);
        when(adsMapperMock.advertEntityToAdsDto(any(Advert.class))).thenReturn(ads);
        assertEquals(ads, out.createAds(createAds));
        verify(advertRepositoryMock, times(1)).save(advert);
    }

    @Test
    public void testShouldThrowExceptionWhenAdsNotFoundInGetRequest() {
        when(advertRepositoryMock.findById(any())).thenThrow(AdvertNotFoundException.class);
        assertThrows(AdvertNotFoundException.class, () -> out.getAds(ADS_ID));
    }

    @Test
    public void testShouldGetAdsById() {
        when(advertRepositoryMock.findById(ADS_ID)).thenReturn(Optional.of(advert));
        when(adsMapperMock.advertEntityToFullAdsDto(any(Advert.class))).thenReturn(fullAds);
        assertEquals(fullAds, out.getAds(ADS_ID));
    }

    @Test
    public void testShouldThrowExceptionWhenAdsNotFoundInDeleteRequest() {
        when(advertRepositoryMock.findById(any())).thenThrow(AdvertNotFoundException.class);
        assertThrows(AdvertNotFoundException.class, () -> out.removeAds(ADS_ID));
    }

    @Test
    public void testShouldUpdateAds() {
        advert.setPrice(PRICE_2);
        ads.setPrice(PRICE_2);
        when(advertRepositoryMock.findById(ADS_ID)).thenReturn(Optional.of(advert));
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(users));
        assertEquals(ads, out.updateAdvert(ADS_ID, ads));
    }
}
