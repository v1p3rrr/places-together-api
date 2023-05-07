package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.GroupPlace;
import com.vpr.placestogetherapi.model.entity.MarkPlace;
import com.vpr.placestogetherapi.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkPlaceRepository extends JpaRepository<MarkPlace, Long> {
    Optional<MarkPlace> findByProfileAndGroupPlace(Profile profile, GroupPlace groupPlace);
    Integer countByGroupPlace(GroupPlace groupPlace);

    List<MarkPlace> findByProfileIdAndGroupPlaceGroupId(Long profileId, Long groupId);
    List<MarkPlace> findByIdAndProfileIdAndGroupPlaceGroupId(Long id, Long profileId, Long groupId);
    List<MarkPlace> findByGroupPlaceGroupIdAndGroupPlace_Place_dgisId(Long profileId, Long dgisId);
    List<MarkPlace> findByGroupPlaceGroupIdAndGroupPlacePlaceName(Long profileId, String placeName);
    Optional<MarkPlace> findByGroupPlaceGroupIdAndProfileIdAndGroupPlacePlaceName(Long groupId, Long profileId, String placeName);
    Optional<MarkPlace> findByGroupPlaceGroupIdAndProfileIdAndGroupPlace_Place_dgisId(Long groupId, Long profileId, Long dgisId);
    List<MarkPlace> findByGroupPlaceGroupId(Long groupId);
}
