package ru.skypro.homework.service;


import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;

public interface AdvertService {

    ResponseWrapperAds getAllAds();

    Ads createAds(CreateAds createAdsDto);

    FullAds getAds(Integer id);

    void removeAds(Integer id);

    Ads updateAdvert(Integer id, Ads adsDto);
}
