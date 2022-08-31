package ru.skypro.homework.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsTo;
import ru.skypro.homework.dto.CreateAdsTo;
import ru.skypro.homework.dto.FullAdsTo;
import ru.skypro.homework.dto.ResponseWrapperAdsTo;

public interface AdvertService {

    ResponseWrapperAdsTo getAllAds();

    FullAdsTo getAds(Integer id);

    AdsTo createAds(CreateAdsTo createAdsDto, MultipartFile file, Authentication authentication);

    void removeAds(Integer id, String username, UserDetails userDetails);

    AdsTo updateAdvert(Integer id, AdsTo adsDto, String username, UserDetails userDetails, MultipartFile file);

    ResponseWrapperAdsTo findAds(String search);

    ResponseWrapperAdsTo getAdsMe(String name);
}
