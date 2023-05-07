package com.vpr.placestogetherapi.repository;

import com.vpr.placestogetherapi.model.entity.GroupPlace;
import com.vpr.placestogetherapi.model.entity.RatingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingPlaceRepository extends JpaRepository<RatingPlace, Long> {
    Optional<RatingPlace> findByIdAndGroupPlace(Long id, GroupPlace groupPlace);
    List<RatingPlace> findByGroupPlaceGroupIdAndProfileId(Long groupId, Long profileId);
    List<RatingPlace> findByGroupPlaceGroupIdAndGroupPlace_Place_dgisId(Long groupId, Long dgisId);
    List<RatingPlace> findByGroupPlaceGroupIdAndGroupPlacePlaceName(Long groupId, String placeName);
    Optional<RatingPlace> findByGroupPlaceGroupIdAndProfileIdAndGroupPlace_Place_dgisId(Long groupId, Long profileId, Long dgisId);
    Optional<RatingPlace> findByGroupPlaceGroupIdAndProfileIdAndGroupPlacePlaceName(Long groupId, Long profileId, String placeName);


}
