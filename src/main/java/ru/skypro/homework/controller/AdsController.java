package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

    @PostMapping
    public ResponseEntity<AdsComment> addAds(@RequestBody AdsComment ads) {
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe() {
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable int id) {
        return ResponseEntity.ok(new FullAds());
    }

    @PatchMapping("{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable int id, @RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }

    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<ResponseWrapperAdsComment> getAdsComments(@PathVariable int ad_pk) {
        return ResponseEntity.ok(new ResponseWrapperAdsComment());
    }

    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<AdsComment> addAdsComment(@PathVariable int ad_pk, @RequestBody AdsComment ads) {
        return ResponseEntity.ok(ads);
    }

    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsComment> getAdsComment(@PathVariable int ad_pk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(new AdsComment());
    }

    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsComment> updateAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id,
                                                    @RequestBody AdsComment comment) {
        return ResponseEntity.ok(comment);
    }
}
