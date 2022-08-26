package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.model.AdsAvatar;
import ru.skypro.homework.repository.AdsAvatarRepository;
import ru.skypro.homework.service.AdsAvatarService;
import ru.skypro.homework.service.AdvertService;

import java.io.IOException;
import java.util.UUID;

@Service
public class AdsAvatarServiceImpl implements AdsAvatarService {

    private final AdsAvatarRepository adsAvatarRepository;

    public AdsAvatarServiceImpl(AdsAvatarRepository adsAvatarRepository) {
        this.adsAvatarRepository = adsAvatarRepository;
    }

    @Override
    public String saveAds(MultipartFile image) {
        System.out.println("Save ads service was called");
        AdsAvatar adsAvatar = new AdsAvatar();
        try {
            // код, который кладет картинку в entity
            byte[] bytes = image.getBytes();
            adsAvatar.setImage(bytes);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        adsAvatar.setId(UUID.randomUUID().toString());
        // код сохранения картинки в БД
        AdsAvatar savedAdsAvatar = adsAvatarRepository.save(adsAvatar);
        return savedAdsAvatar.getId();
    }

    @Override
    public byte [] getAdsAvatar(String id) {
        System.out.println("Аватар зопрошен");
        return adsAvatarRepository.findById(id).get().getImage();
    }
}
