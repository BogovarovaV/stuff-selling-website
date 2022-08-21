package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.exception.AdvertNotFoundException;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Advert;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdvertService;

import java.util.List;

@Service
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final AdsMapper adsMapper;

    public AdvertServiceImpl(AdvertRepository advertRepository, UserRepository userRepository, AdsMapper adsMapper) {
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.adsMapper = adsMapper;
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        List<Ads> adsDtoList = adsMapper.advertEntitiesToAdsDtos(advertRepository.findAllAdverts());
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
        return responseWrapperAds;
    }

    @Override
    public Ads createAds(CreateAds createAdsDto) {
        Advert createdAds = adsMapper.createAdsDtoToAdvertEntity(createAdsDto);
        advertRepository.save(createdAds);
        return adsMapper.advertEntityToAdsDto(createdAds);
    }

    @Override
    public void removeAds(Integer id, String username, UserDetails userDetails) {
        Advert advert = advertRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN")
                || username.equals(advert.getUsers().getUsername())) {
            advertRepository.delete(advert);
        } else {
            throw new NoAccessException();
        }
    }

    @Override
    public FullAds getAds(Integer id) {
        Advert advert = advertRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        return adsMapper.advertEntityToFullAdsDto(advert);
    }

    @Override
    public Ads updateAdvert(Integer id, Ads adsDto, String username, UserDetails userDetails) {
        Advert advert = advertRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN")
                || username.equals(advert.getUsers().getUsername())) {
            advert.setUsers(userRepository.findById(adsDto.getAuthor()).orElseThrow(UserNotFoundException::new));
            advert.setImage(adsDto.getImage());
            advert.setPrice(adsDto.getPrice());
            advert.setTitle(adsDto.getTitle());
            advertRepository.save(advert);
            return adsDto;
        } else {
            throw new NoAccessException();
        }
    }

    @Override
    public ResponseWrapperAds findAds(String search) {
        List<Ads> adsDtoList = adsMapper.advertEntitiesToAdsDtos(advertRepository.findAds(search));
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
        return responseWrapperAds;
    }

    @Override
    public ResponseWrapperAds getAdsMe(String username) {
        Users users = userRepository.findUsersByUsername(username).orElseThrow(UserNotFoundException::new);
        List<Ads> adsDtoList = adsMapper.advertEntitiesToAdsDtos(advertRepository.findAdsByUsersId(users.getId()));
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
        return responseWrapperAds;
    }
}
