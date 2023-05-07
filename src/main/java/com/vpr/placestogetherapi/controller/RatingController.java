package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.RatingPlace;
import com.vpr.placestogetherapi.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("/{groupId}/add/{profileId}/{placeDgisId}/{stars}")
    public ResponseEntity<RatingPlace> addRating(@PathVariable Long profileId, @PathVariable Long groupId, @PathVariable Long placeDgisId, @PathVariable Integer stars) {
        RatingPlace ratingPlace = ratingService.addRating(profileId, groupId, placeDgisId, stars);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingPlace);
    }

    @PutMapping("/{groupId}/update/{editorProfileId}/{placeDgisId}/{ratingId}/{newStars}")
    public ResponseEntity<RatingPlace> updateRating(@PathVariable Long editorProfileId, @PathVariable Long groupId, @PathVariable Long placeDgisId, @PathVariable Long ratingId, @PathVariable Integer newStars) {
        RatingPlace updatedRatingPlace = ratingService.updateRating(editorProfileId, groupId, placeDgisId, ratingId, newStars);
        return ResponseEntity.ok(updatedRatingPlace);
    }

    @DeleteMapping("/{groupId}/delete/{editorProfileId}/{placeDgisId}/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long editorProfileId, @PathVariable Long groupId, @PathVariable Long placeDgisId, @PathVariable Long ratingId) {
        ratingService.deleteRating(editorProfileId, groupId, placeDgisId, ratingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}/by-profile/{profileId}")
    public ResponseEntity<List<RatingPlace>> getRatingsByGroupIdAndProfileId(@PathVariable Long groupId, @PathVariable Long profileId) {
        List<RatingPlace> ratings = ratingService.getCommentsByGroupIdAndProfileId(groupId, profileId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{groupId}/by-dgis/{dgisId}")
    public ResponseEntity<List<RatingPlace>> getRatingsByGroupIdAndDgisId(@PathVariable Long groupId, @PathVariable Long dgisId) {
        List<RatingPlace> ratings = ratingService.getCommentsByGroupIdAndDgisId(groupId, dgisId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{groupId}/by-name/{placeName}")
    public ResponseEntity<List<RatingPlace>> getRatingsByGroupIdAndPlaceName(@PathVariable Long groupId, @PathVariable String placeName) {
        List<RatingPlace> ratings = ratingService.getCommentsByGroupIdAndPlaceName(groupId, placeName);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{groupId}/by-profile/{profileId}/by-dgis/{dgisId}")
    public ResponseEntity<RatingPlace> getRatingByGroupIdAndProfileIdAndDgisId(@PathVariable Long groupId, @PathVariable Long profileId, @PathVariable Long dgisId) {
        RatingPlace ratingPlace = ratingService.getCommentByGroupIdAndProfileIdAndDgisId(groupId, profileId, dgisId);
        return ResponseEntity.ok(ratingPlace);
    }

    @GetMapping("/{groupId}/by-profile/{profileId}/by-name/{placeName}")
    public ResponseEntity<RatingPlace> getRatingByGroupIdAndProfileIdAndPlaceName(@PathVariable Long groupId, @PathVariable Long profileId, @PathVariable String placeName) {
        RatingPlace ratingPlace = ratingService.getCommentByGroupIdAndProfileIdAndPlaceName(groupId, profileId, placeName);
        return ResponseEntity.ok(ratingPlace);
    }

}

