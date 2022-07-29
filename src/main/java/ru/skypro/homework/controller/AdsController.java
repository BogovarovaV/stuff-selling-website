package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка всех объявлений (getAllAds)"
    )
    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Добавление объявления (addAds)"
    )
    @PostMapping
    public ResponseEntity<AdsComment> addAds(@RequestBody AdsComment ads) {
        return ResponseEntity.ok(ads);
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка объявлений пользователя (getAdsMe)"
    )
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe() {
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Удаление объявления по id (removeAds)"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение объявления по id (getAds)"
    )
    @GetMapping("{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable int id) {
        return ResponseEntity.ok(new FullAds());
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Редактирование объявления по id (updateAds)"
    )
    @PatchMapping("{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable int id, @RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Получение списка отзывов объявления (getAdsComments)"
    )
    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<ResponseWrapperAdsComment> getAdsComments(@PathVariable int ad_pk) {
        return ResponseEntity.ok(new ResponseWrapperAdsComment());
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Добавление отзыва к объявлению (addAdsComment)"
    )
    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<AdsComment> addAdsComment(@PathVariable int ad_pk, @RequestBody AdsComment ads) {
        return ResponseEntity.ok(ads);
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Удаление отзыва по id (deleteAdsComment)"
    )
    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Получение отзыва к объявлению по id (getAdsComment)"
    )
    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsComment> getAdsComment(@PathVariable int ad_pk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(new AdsComment());
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Редактирование отзыва по id (updateAdsComment)"
    )
    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsComment> updateAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id,
                                                    @RequestBody AdsComment comment) {
        return ResponseEntity.ok(comment);
    }
}
