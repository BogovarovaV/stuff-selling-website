package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;

import java.util.Collection;

@RestController
@RequestMapping("/ads")
public class AdsController {

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка всех объявлений (getAllAds)"
    )
    @GetMapping
    public ResponseEntity<Collection<Ads>> getAllAds() {
        return ResponseEntity.ok(null);
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Добавление объявления (addAds)"
    )
    @PostMapping
    public ResponseEntity<Ads> addAds(@RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка объявлений пользователя (getAdsMe)"
    )
    @GetMapping("/me")
    public ResponseEntity<Collection<Ads>> getAdsMe() {
        return ResponseEntity.ok(null);
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Удаление объявления по id (removeAds)"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Ads> removeAds(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение объявления по id (getAds)"
    )
    @GetMapping("{id}")
    public ResponseEntity<Ads> getAds(@PathVariable int id) {
        return ResponseEntity.ok(new Ads());
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
    public ResponseEntity<Collection<Comment>> getAdsComments(@PathVariable int ad_pk) {
        return ResponseEntity.ok(null);
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Добавление отзыва к объявлению (addAdsComment)"
    )
    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<Ads> addAdsComment(@PathVariable int ad_pk, @RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Удаление отзыва по id (deleteAdsComment)"
    )
    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Comment> deleteAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Получение отзыва к объявлению по id (getAdsComment)"
    )
    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Comment> getAdsComment(@PathVariable int ad_pk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(new Comment());
    }

    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Редактирование отзыва по id (updateAdsComment)"
    )
    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Comment> updateAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id,
                                                    @RequestBody Comment comment) {
        return ResponseEntity.ok(comment);
    }
}
