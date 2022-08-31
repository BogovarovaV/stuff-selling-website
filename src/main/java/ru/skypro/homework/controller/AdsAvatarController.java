package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Operation(
            tags = "Фото объявлений (AdsAvatarController)",
            summary = "Сохранение изображения объявления (saveAds)"
    )
    @PostMapping(value = "/upl", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveAds(@RequestParam MultipartFile image) {
        return adsAvatarService.saveAds(image);
    }

    @Operation(
            tags = "Фото объявлений (AdsAvatarController)",
            summary = "Получение изображения объявления по id (getAdsAvatar)"
    )
    @GetMapping(value = "/api/{id}/image", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsAvatar(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(adsAvatarService.getAdsAvatar(id));
    }
}
