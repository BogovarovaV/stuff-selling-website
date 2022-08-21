package ru.skypro.homework.service;


import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;

public interface AdvertService {

    ResponseWrapperAds getAllAds();

    Ads createAds(CreateAds createAdsDto);

    FullAds getAds(Integer id);

    void removeAds(Integer id, String username, UserDetails userDetails);

    Ads updateAdvert(Integer id, Ads adsDto, String username, UserDetails userDetails);

    ResponseWrapperAds findAds(String search);

    ResponseWrapperAds getAdsMe(String name);
}
