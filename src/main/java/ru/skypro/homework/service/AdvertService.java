package ru.skypro.homework.service;


import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.model.Advert;

public interface AdvertService {

    ResponseWrapperAds getAllAds();

    void createAds(CreateAds createAdsDto);

    FullAds getAds(Integer id);

    void removeAds(Integer id);

    Ads updateAdvert(Integer id, Ads adsDto);

    Advert convertAdsDtoToAdvertEntity(Ads adsDto);

    Advert convertCreateAdsDtoToAdvertEntity(CreateAds createAdsDto);

    FullAds convertAdvertEntityToFullAdsDto(Advert advert);
}
