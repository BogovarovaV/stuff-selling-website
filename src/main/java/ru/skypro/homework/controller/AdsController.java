package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsAvatarService;
import ru.skypro.homework.service.AdvertService;
import ru.skypro.homework.service.CommentService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    private final AdvertService advertService;
    private final CommentService commentService;
    private final AdsAvatarService adsAvatarService;

    public AdsController(AdvertService advertService, CommentService commentService, AdsAvatarService adsAvatarService) {
        this.advertService = advertService;
        this.commentService = commentService;
        this.adsAvatarService = adsAvatarService;
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка всех объявлений (getAllAds)"
    )

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        System.out.println("Зопрошено");
        return ResponseEntity.ok(advertService.getAllAds());
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Добавление объявления (addAds)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<Ads> createAds(@ModelAttribute CreateAds ads, @RequestParam MultipartFile image) {
        System.out.println("Create ads called");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(advertService.createAds(ads, image, authentication));
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка объявлений пользователя (getAdsMe)"
    )
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(advertService.getAdsMe(authentication.getName()));
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        advertService.removeAds(id, authentication.getName(), userDetails);
        return ResponseEntity.ok().build();
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение объявления по id (getAds)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable int id) {
        return ResponseEntity.ok(advertService.getAds(id));
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Редактирование объявления по id (updateAds)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PatchMapping("{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable int id, @RequestBody Ads ads) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(advertService.updateAdvert(id, ads, authentication.getName(), userDetails));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Получение списка отзывов объявления (getAdsComments)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<ResponseWrapperAdsComment> getAdsComments(@PathVariable int ad_pk) {
        return ResponseEntity.ok(commentService.getAdsAllComments(ad_pk));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Добавление отзыва к объявлению (addAdsComment)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<AdsComment> addAdsComment(@PathVariable int ad_pk, @RequestBody AdsComment adsComment) {
        return ResponseEntity.ok(commentService.createComment(ad_pk, adsComment));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Удаление отзыва по id (deleteAdsComment)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        commentService.deleteAdsComment(ad_pk, id, authentication.getName(), userDetails);
        return ResponseEntity.ok().build();
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Получение отзыва к объявлению по id (getAdsComment)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsComment> getAdsComment(@PathVariable int ad_pk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(commentService.getAdsComment(ad_pk, id));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Редактирование отзыва по id (updateAdsComment)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsComment> updateAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id,
                                                    @RequestBody AdsComment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(commentService.updateAdsComment(ad_pk, id, comment, authentication.getName(), userDetails));
    }
}
