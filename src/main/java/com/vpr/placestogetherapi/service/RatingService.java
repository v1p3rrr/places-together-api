package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.RatingPlace;

import java.util.List;

public interface RatingService {
    RatingPlace addRating(Long profileId, Long groupId, Long placeDgisId, Integer stars);

    RatingPlace updateRating(Long editorProfileId, Long groupId, Long placeDgisId, Long commentId, Integer newStars);

    void deleteRating(Long editorProfileId, Long groupId, Long placeDgisId, Long ratingId);

    List<RatingPlace> getRatingsByGroupIdAndProfileId(Long groupId, Long profileId);
    List<RatingPlace> getRatingsByGroupIdAndDgisId(Long groupId, Long dgisId);
    List<RatingPlace> getRatingsByGroupIdAndPlaceName(Long groupId, String placeName);
    RatingPlace getRatingByGroupIdAndProfileIdAndDgisId(Long groupId, Long profileId, Long dgisId);
    RatingPlace getRatingByGroupIdAndProfileIdAndPlaceName(Long groupId, Long profileId, String placeName);
}
