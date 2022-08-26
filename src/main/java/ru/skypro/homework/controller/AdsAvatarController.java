package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.AdsAvatarService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsAvatarController {

    private final AdsAvatarService adsAvatarService;

    public AdsAvatarController(AdsAvatarService adsAvatarService) {
        this.adsAvatarService = adsAvatarService;
    }

    @PostMapping(value = "/upl", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveAds(@RequestParam MultipartFile image) {
        return adsAvatarService.saveAds(image);
    }

    @GetMapping(value = "/api/{id}/image", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getAdsAvatar(@PathVariable("id") String id) {
        System.out.println("Get ads by id controller called");
        return adsAvatarService.getAdsAvatar(id);
    }
}
