package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;

import java.util.Collection;

@RestController
@RequestMapping("/ads")
public class AdsController {

    @GetMapping
    public ResponseEntity<Collection<Ads>> getAllAds() {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<Ads> addAds(@RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/me")
    public ResponseEntity<Collection<Ads>> getAdsMe() {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Ads> removeAds(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Ads> getAds(@PathVariable int id) {
        return ResponseEntity.ok(new Ads());
    }

    @PatchMapping("{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable int id, @RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }

    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<Collection<Comment>> getAdsComments(@PathVariable int ad_pk) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<Ads> addAdsComment(@PathVariable int ad_pk, @RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }

    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Comment> deleteAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Comment> getAdsComment(@PathVariable int ad_pk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(new Comment());
    }

    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Comment> updateAdsComment(@PathVariable int ad_pk,
                                                    @PathVariable int id,
                                                    @RequestBody Comment comment) {
        return ResponseEntity.ok(comment);
    }
}
