package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdvertService;
import ru.skypro.homework.service.CommentService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    private final AdvertService advertService;
    private final CommentService commentService;

    public AdsController(AdvertService advertService, CommentService commentService) {
        this.advertService = advertService;
        this.commentService = commentService;
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка всех объявлений (getAllAds)"
    )

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(advertService.getAllAds());
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Добавление объявления (addAds)"
    )
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    @PostMapping
    public ResponseEntity<Ads> addAds(@RequestBody CreateAds ads) {
        return ResponseEntity.ok(advertService.createAds(ads));
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
            summary = "Получение списка объявлений в результате поиска (findAds) или получение списка всех объявлений (getAllAds)"
    )
    @GetMapping(params = {"search"})
    public ResponseEntity<ResponseWrapperAds> findAds(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(advertService.findAds(search));
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Удаление объявления по id (removeAds)"
    )
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id") //?
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id) {
        advertService.removeAds(id);
        return ResponseEntity.ok().build();
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение объявления по id (getAds)"
    )
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id") //?
    @GetMapping("{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable int id) {
        return ResponseEntity.ok(advertService.getAds(id));
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Редактирование объявления по id (updateAds)"
    )
    @PreAuthorize("hasRole('ADMIN') or #ads.author == authentication.principal.id")
    @PatchMapping("{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable int id, @RequestBody Ads ads) {
        return ResponseEntity.ok(advertService.updateAdvert(id, ads));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Получение списка отзывов объявления (getAdsComments)"
    )
    @PreAuthorize("hasRole('ADMIN') or #ad_pk == authentication.principal.id") //?
    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<ResponseWrapperAdsComment> getAdsComments(@PathVariable int ad_pk) {
        return ResponseEntity.ok(commentService.getAdsAllComments(ad_pk));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Добавление отзыва к объявлению (addAdsComment)"
    )
    @PreAuthorize("hhasRole('ADMIN') or #ad_pk == authentication.principal.id")
    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<AdsComment> addAdsComment(@PathVariable int ad_pk, @RequestBody AdsComment adsComment) {
        return ResponseEntity.ok(commentService.createComment(ad_pk, adsComment));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Удаление отзыва по id (deleteAdsComment)"
    )
    @PreAuthorize("hasRole('ADMIN') or #ad_pk == authentication.principal.id")
    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id) {
        commentService.deleteAdsComment(ad_pk, id);
        return ResponseEntity.ok().build();
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Получение отзыва к объявлению по id (getAdsComment)"
    )
    @PreAuthorize("hasRole('ADMIN') or #ad_pk == authentication.principal.id")
    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsComment> getAdsComment(@PathVariable int ad_pk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(commentService.getAdsComment(ad_pk, id));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Редактирование отзыва по id (updateAdsComment)"
    )
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsComment> updateAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id,
                                                    @RequestBody AdsComment comment) {
        return ResponseEntity.ok(commentService.updateAdsComment(ad_pk, id, comment));
    }
}
