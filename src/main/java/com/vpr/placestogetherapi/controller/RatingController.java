package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.RatingPlace;
import com.vpr.placestogetherapi.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@Tag(name = "Rating API", description = "APIs for managing rating of places within groups")
public class RatingController {
    private final RatingService ratingService;

        @PostMapping("/{groupId}/add/{profileId}/{placeDgisId}/{stars}")
        @Operation(summary = "Add a rating for a place")
        public ResponseEntity<RatingPlace> addRating(
                @Parameter(description = "The user profile id") @PathVariable Long profileId,
                @Parameter(description = "The group id") @PathVariable Long groupId,
                @Parameter(description = "The place dgis id") @PathVariable Long placeDgisId,
                @Parameter(description = "The number of stars for the rating (1-5)") @PathVariable Integer stars) {
            RatingPlace ratingPlace = ratingService.addRating(profileId, groupId, placeDgisId, stars);
            return ResponseEntity.status(HttpStatus.CREATED).body(ratingPlace);
        }

        @PutMapping("/{groupId}/update/{editorProfileId}/{placeDgisId}/{ratingId}/{newStars}")
        @Operation(summary = "Update a rating for a place")
        public ResponseEntity<RatingPlace> updateRating(
                @Parameter(description = "The user profile id of the editor") @PathVariable Long editorProfileId,
                @Parameter(description = "The group id") @PathVariable Long groupId,
                @Parameter(description = "The place dgis id") @PathVariable Long placeDgisId,
                @Parameter(description = "The rating id") @PathVariable Long ratingId,
                @Parameter(description = "The new number of stars for the rating (1-5)") @PathVariable Integer newStars) {
            RatingPlace updatedRatingPlace = ratingService.updateRating(editorProfileId, groupId, placeDgisId, ratingId, newStars);
            return ResponseEntity.ok(updatedRatingPlace);
        }

        @DeleteMapping("/{groupId}/delete/{editorProfileId}/{placeDgisId}/{ratingId}")
        @Operation(summary = "Delete a rating for a place")
        public ResponseEntity<Void> deleteRating(
                @Parameter(description = "The user profile id of the editor") @PathVariable Long editorProfileId,
                @Parameter(description = "The group id") @PathVariable Long groupId,
                @Parameter(description = "The place dgis id") @PathVariable Long placeDgisId,
                @Parameter(description = "The rating id") @PathVariable Long ratingId) {
            ratingService.deleteRating(editorProfileId, groupId, placeDgisId, ratingId);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/{groupId}/by-profile/{profileId}")
        @Operation(summary = "Get all ratings by a user in a group")
        public ResponseEntity<List<RatingPlace>> getRatingsByGroupIdAndProfileId(
                @Parameter(description = "The group id") @PathVariable Long groupId,
                @Parameter(description = "The user profile id") @PathVariable Long profileId) {
            List<RatingPlace> ratings = ratingService.getRatingsByGroupIdAndProfileId(groupId, profileId);
            return ResponseEntity.ok(ratings);
        }

        @GetMapping("/{groupId}/by-dgis/{dgisId}")
        @Operation(summary = "Get all ratings for a place in a group")
        public ResponseEntity<List<RatingPlace>> getRatingsByGroupIdAndDgisId(
                @Parameter(description = "The group id") @PathVariable Long groupId,
                @Parameter(description = "The place dgis id") @PathVariable Long dgisId) {
            List<RatingPlace> ratings = ratingService.getRatingsByGroupIdAndDgisId(groupId, dgisId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{groupId}/by-name/{placeName}")
    @Operation(summary = "Get all ratings by group id and place name")
    public ResponseEntity<List<RatingPlace>> getRatingsByGroupIdAndPlaceName(
            @Parameter(description = "The group id") @PathVariable Long groupId,
            @Parameter(description = "The place name") @PathVariable String placeName) {
        List<RatingPlace> ratings = ratingService.getRatingsByGroupIdAndPlaceName(groupId, placeName);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{groupId}/by-profile/{profileId}/by-dgis/{dgisId}")
    @Operation(summary = "Get rating by group id, profile id and DGIS id")
    public ResponseEntity<RatingPlace> getRatingByGroupIdAndProfileIdAndDgisId(
            @Parameter(description = "The group id") @PathVariable Long groupId,
            @Parameter(description = "The profile id") @PathVariable Long profileId,
            @Parameter(description = "The DGIS id") @PathVariable Long dgisId) {
        RatingPlace ratingPlace = ratingService.getRatingByGroupIdAndProfileIdAndDgisId(groupId, profileId, dgisId);
        return ResponseEntity.ok(ratingPlace);
    }

    @GetMapping("/{groupId}/by-profile/{profileId}/by-name/{placeName}")
    @Operation(summary = "Get rating by group id, profile id and place name")
    public ResponseEntity<RatingPlace> getRatingByGroupIdAndProfileIdAndPlaceName(
            @Parameter(description = "The group id") @PathVariable Long groupId,
            @Parameter(description = "The profile id") @PathVariable Long profileId,
            @Parameter(description = "The place name") @PathVariable String placeName) {
        RatingPlace ratingPlace = ratingService.getRatingByGroupIdAndProfileIdAndPlaceName(groupId, profileId, placeName);
        return ResponseEntity.ok(ratingPlace);
    }

}

