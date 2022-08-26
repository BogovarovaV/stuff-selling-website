package ru.skypro.homework.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;

public interface AdvertService {

    ResponseWrapperAds getAllAds();

    FullAds getAds(Integer id);

    Ads createAds(CreateAds createAdsDto, MultipartFile file, Authentication authentication);

    void removeAds(Integer id, String username, UserDetails userDetails);

    Ads updateAdvert(Integer id, Ads adsDto, String username, UserDetails userDetails, MultipartFile file);

    ResponseWrapperAds findAds(String search);

    ResponseWrapperAds getAdsMe(String name);
}
