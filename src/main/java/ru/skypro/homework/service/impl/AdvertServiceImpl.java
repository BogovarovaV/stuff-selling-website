package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.exception.AdvertNotFoundException;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Advert;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsAvatarService;
import ru.skypro.homework.service.AdvertService;

import java.util.List;

@Service
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final AdsMapper adsMapper;
    private final AdsAvatarService adsAvatarService;


    public AdvertServiceImpl(AdvertRepository advertRepository, UserRepository userRepository, AdsMapper adsMapper, AdsAvatarService adsAvatarService) {
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.adsMapper = adsMapper;
        this.adsAvatarService = adsAvatarService;
    }

    /**
     * Get a list of all adverts
     *
     * @return list as ResponseWrapperAds (DTO)
     */
    @Override
    public ResponseWrapperAds getAllAds() {
        System.out.println("Get all ads service called");
        List<Ads> adsDtoList = adsMapper.advertEntitiesToAdsDtos(advertRepository.findAll());
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
        return responseWrapperAds;
    }

    /**
     * Create advert
     * // * @param createAdsDto - advert from client
     *
     * @return created advert as Ads (DTO)
     */

    @Override
    public Ads createAds(CreateAds createAdsDto, MultipartFile file, Authentication authentication) {
        Advert createdAds = adsMapper.createAdsDtoToAdvertEntity(createAdsDto);
        createdAds.setUser(userRepository.findUsersByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new));
        createdAds.setImage("/api/" + adsAvatarService.saveAds(file) + "/image");
        advertRepository.save(createdAds);
        return adsMapper.advertEntityToAdsDto(createdAds);
    }

    /**
     * remove advert by ID
     *
     * @param id          - advert id
     * @param username    - username from client
     * @param userDetails - user details from client
     */
    @Override
    public void removeAds(Integer id, String username, UserDetails userDetails) {
        Advert advert = advertRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        // check if user has access to delete advert (has role "Admin" or user wants to delete his own advert)
        if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN")
                || username.equals(advert.getUser().getUsername())) {
            advertRepository.delete(advert);
        } else {
            throw new NoAccessException();
        }
    }

    /**
     * Get advert by ID
     *
     * @param id - advert ID
     * @return found advert as FullAds (DTO)
     */
    @Override
    public FullAds getAds(Integer id) {
        Advert advert = advertRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        return adsMapper.advertEntityToFullAdsDto(advert);
    }

    /**
     * Update advert by ID
     *
     * @param id          - advert ID
     * @param adsDto      - advert information as Ads (DTO) from client
     * @param username    - username from client
     * @param userDetails - user details from client
     * @return updated advert as Ads (DTO) or throw exception
     */
    @Override
    public Ads updateAdvert(Integer id, Ads adsDto, String username, UserDetails userDetails, MultipartFile file) {
        Advert advert = advertRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        // check if user has access to change advert (has role "Admin" or user wants to change his own advert)
        if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN")
                || username.equals(advert.getUser().getUsername())) {
            advert.setImage(adsAvatarService.saveAds(file));
            advert.setPrice(adsDto.getPrice());
            advert.setTitle(adsDto.getTitle());
            advert.setDescription(adsDto.getDescription());
            advertRepository.save(advert);
            return adsMapper.advertEntityToAdsDto(advert);
        } else {
            throw new NoAccessException();
        }
    }

    /**
     * Find adverts by keyword(s)
     *
     * @param search - keyword(s) from client
     * @return list of adverts finding by keyword(s) as ResponseWrapperAds (DTO)
     */
    @Override
    public ResponseWrapperAds findAds(String search) {
        List<Ads> adsDtoList = adsMapper.advertEntitiesToAdsDtos(advertRepository.findAllByTitleContainsIgnoreCase(search));
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
        return responseWrapperAds;
    }

    /**
     * Get a list of all adverts of a specific user
     *
     * @param username - username from client
     * @return list of finding adverts of a specific user
     * as ResponseWrapperAds (DTO)
     */
    @Override
    public ResponseWrapperAds getAdsMe(String username) {
        User user = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        List<Ads> adsDtoList = adsMapper.advertEntitiesToAdsDtos(advertRepository.findAllByUserId(user.getId()));
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
        return responseWrapperAds;
    }
}
