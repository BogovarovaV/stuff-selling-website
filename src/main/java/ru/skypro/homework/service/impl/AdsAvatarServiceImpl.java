package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.FailedToSaveAdsAvatarException;
import ru.skypro.homework.model.AdsAvatar;
import ru.skypro.homework.repository.AdsAvatarRepository;
import ru.skypro.homework.service.AdsAvatarService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
public class AdsAvatarServiceImpl implements AdsAvatarService {

    private final AdsAvatarRepository adsAvatarRepository;

    public AdsAvatarServiceImpl(AdsAvatarRepository adsAvatarRepository) {
        this.adsAvatarRepository = adsAvatarRepository;
    }

    /**
     * Save advert avatar
     * @param file - advert avatar file from client
     * @return avatar ID as String
     */
    @Override
    public Integer saveAds(MultipartFile file) {
        AdsAvatar adsAvatar = new AdsAvatar();
        try {
            byte[] bytes = file.getBytes();
            adsAvatar.setImage(bytes);
        } catch (
                IOException e) {
            throw new FailedToSaveAdsAvatarException(e);
        }
        AdsAvatar savedAdsAvatar = adsAvatarRepository.saveAndFlush(adsAvatar);
        return savedAdsAvatar.getId();
    }

    /**
     * Get advert avatar by ID
     * @param id - advert avatar ID
     * @return advert avatar as byte array
     */
    @Override
    public byte[] getAdsAvatar(String id) {
        Optional<AdsAvatar> adsAvatar = adsAvatarRepository.findById(id);
        byte[] image = null;
        if (adsAvatarRepository.findById(id).isPresent()) {
            image = adsAvatar.get().getImage();
        }
        return image;
    }
}
