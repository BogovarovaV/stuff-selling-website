package ru.skypro.homework.controller;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdvertService;
import ru.skypro.homework.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Validated
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
    public ResponseEntity<ResponseWrapperAdsTo> getAllAds() {
        return ResponseEntity.ok(advertService.getAllAds());
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Добавление объявления (addAds)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<AdsTo> createAds(@RequestPart("properties") @Valid CreateAdsTo ads,
                                           @RequestPart("image") @Valid @NotNull MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(advertService.createAds(ads, file, authentication));
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка объявлений пользователя (getAdsMe)"
    )
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResponseWrapperAdsTo> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(advertService.getAdsMe(authentication.getName()));
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Получение списка объявлений в результате поиска (findAds) или получение списка всех объявлений (getAllAds)"
    )
    @GetMapping(params = {"search"})
    public ResponseEntity<ResponseWrapperAdsTo> findAds(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(advertService.findAds(search));
    }


    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Удаление объявления по id (removeAds)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@Positive @PathVariable int id) {
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
    public ResponseEntity<FullAdsTo> getAds(@Positive @PathVariable int id) {
        return ResponseEntity.ok(advertService.getAds(id));
    }

    @Operation(
            tags = "Объявления (AdsController)",
            summary = "Редактирование объявления по id (updateAds)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PatchMapping("{id}")
    public ResponseEntity<AdsTo> updateAds(@PathVariable int id,
                                           @RequestPart("properties") @Valid AdsTo adsTo,
                                           @RequestPart("image") @Valid @NotNull MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(advertService.updateAdvert(id, adsTo, authentication.getName(), userDetails, file));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Получение списка отзывов объявления (getAdsComments)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperAdsCommentTo> getAdsComments(@Positive @PathVariable int ad_pk) {
        return ResponseEntity.ok(commentService.getAdsAllComments(ad_pk));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Добавление отзыва к объявлению (addAdsComment)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("{ad_pk}/comments")
    public ResponseEntity<AdsCommentTo> addAdsComment(@Positive @PathVariable int ad_pk, @Valid @RequestBody AdsCommentTo adsCommentTo) {
        return ResponseEntity.ok(commentService.createComment(ad_pk, adsCommentTo));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Удаление отзыва по id (deleteAdsComment)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @DeleteMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteAdsComment(@Positive @PathVariable int ad_pk,
                                                    @Positive @PathVariable int id) {
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
    @GetMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentTo> getAdsComment(@Positive @PathVariable int ad_pk,
                                                      @Positive @PathVariable int id) {
        return ResponseEntity.ok(commentService.getAdsComment(ad_pk, id));
    }


    @Operation(
            tags = "Отзывы (AdsController)",
            summary = "Редактирование отзыва по id (updateAdsComment)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PatchMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentTo> updateAdsComment(@Positive @PathVariable int ad_pk,
                                                         @Positive@PathVariable int id,
                                                         @Valid @RequestBody AdsCommentTo comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(commentService.updateAdsComment(ad_pk, id, comment, authentication.getName(), userDetails));
    }
}
