package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.RatingPlace;

import java.util.List;

public interface RatingService {
    RatingPlace addRating(Long profileId, Long groupId, Long placeDgisId, Integer stars);

    RatingPlace updateRating(Long editorProfileId, Long groupId, Long placeDgisId, Long commentId, Integer newStars);

    void deleteRating(Long editorProfileId, Long groupId, Long placeDgisId, Long ratingId);

    List<RatingPlace> getCommentsByGroupIdAndProfileId(Long groupId, Long profileId);
    List<RatingPlace> getCommentsByGroupIdAndDgisId(Long groupId, Long dgisId);
    List<RatingPlace> getCommentsByGroupIdAndPlaceName(Long groupId, String placeName);
    RatingPlace getCommentByGroupIdAndProfileIdAndDgisId(Long groupId, Long profileId, Long dgisId);
    RatingPlace getCommentByGroupIdAndProfileIdAndPlaceName(Long groupId, Long profileId, String placeName);
}
