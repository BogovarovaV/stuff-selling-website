package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.model.Advert;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdvertService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;

    public AdvertServiceImpl(AdvertRepository advertRepository, UserRepository userRepository) {
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        return convertAdvertEntityToResponseWrapperAdsDto(advertRepository.findAll());
    }

    @Override
    public void createAds(CreateAds createAdsDto) {
        Advert createdAds = convertCreateAdsDtoToAdvertEntity(createAdsDto);
        advertRepository.save(createdAds);
    }

    @Override
    public void removeAds(Integer id) {
        if (isAdvertExists(id)) {
            advertRepository.deleteById(id);
        }
    }

    @Override
    public FullAds getAds(Integer id) {
        if (isAdvertExists(id)) {
            return convertAdvertEntityToFullAdsDto(advertRepository.findById(id).get());
        }
        return null;
    }

    @Override
    public Ads updateAdvert(Integer id, Ads adsDto) {
        if (isAdvertExists(id)) {
            advertRepository.save(convertAdsDtoToAdvertEntity(adsDto));
            return adsDto;
        }
        return null;
    }

    @Override
    public Advert convertAdsDtoToAdvertEntity(Ads adsDto) {
        Advert editedAdvert = advertRepository.getById(adsDto.getPk());
        editedAdvert.setImage(adsDto.getImage());
        editedAdvert.setPrice(adsDto.getPrice());
        editedAdvert.setTitle(adsDto.getTitle());
        return editedAdvert;
    }

    @Override
    public Advert convertCreateAdsDtoToAdvertEntity(CreateAds createAdsDto) {
        Advert advert = new Advert();
        advert.setPrice(createAdsDto.getPrice());
        advert.setTitle(createAdsDto.getTitle());
        advert.setImage(createAdsDto.getImage());
        advert.setDescription(createAdsDto.getDescription());
        //?????
        advert.setUsers(null);
        //????
        advert.setCommentList(null);
        return advert;
    }

    @Override
    public FullAds convertAdvertEntityToFullAdsDto(Advert advert) {
        FullAds fullAdsDto = new FullAds();
        fullAdsDto.setAuthorFirstName(advert.getUsers().getFirstName());
        fullAdsDto.setAuthorLastName(advert.getUsers().getLastName());
        fullAdsDto.setDescription(advert.getDescription());
        fullAdsDto.setEmail(advert.getUsers().getEmail());
        fullAdsDto.setImage(advert.getImage());
        fullAdsDto.setPhone(advert.getUsers().getPhone());
        fullAdsDto.setPk(advert.getId());
        fullAdsDto.setPrice(advert.getPrice());
        fullAdsDto.setTitle(advert.getTitle());
        return fullAdsDto;
    }

    private ResponseWrapperAds convertAdvertEntityToResponseWrapperAdsDto(List<Advert> allAdverts) {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        List<Ads> list = new ArrayList<>(allAdverts.size());
        for (Advert advert : allAdverts) {
            list.add(convertAdvertEntityToAdsDto(advert));
        }
        responseWrapperAds.setCount(list.size());
        responseWrapperAds.setResults(list);
        return responseWrapperAds;
    }

    private Ads convertAdvertEntityToAdsDto(Advert advert) {
        Ads adsDto = new Ads();
        adsDto.setAuthor(advert.getUsers().getId());
        adsDto.setImage(adsDto.getImage());
        adsDto.setPrice(adsDto.getPrice());
        adsDto.setPk(adsDto.getPk());
        adsDto.setTitle(adsDto.getTitle());
        return adsDto;
    }

    private boolean isAdvertExists(Integer id) {
        return advertRepository.findById(id).isPresent();
    }
}
