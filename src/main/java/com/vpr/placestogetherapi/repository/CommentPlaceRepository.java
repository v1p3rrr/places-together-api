package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.CommentPlace;
import com.vpr.placestogetherapi.model.entity.GroupPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentPlaceRepository extends JpaRepository<CommentPlace, Long> {
    Optional<CommentPlace> findByIdAndGroupPlace(Long id, GroupPlace groupPlace);
    List<CommentPlace> findByGroupPlaceGroupIdAndProfileId(Long groupId, Long profileId);
    List<CommentPlace> findByGroupPlaceGroupIdAndGroupPlace_Place_dgisId(Long groupId, Long dgisId);
    List<CommentPlace> findByGroupPlaceGroupIdAndGroupPlacePlaceName(Long groupId, String placeName);
    List<CommentPlace> findByGroupPlaceGroupIdAndProfileIdAndGroupPlace_Place_dgisId(Long groupId, Long profileId, Long dgisId);
    List<CommentPlace> findByGroupPlaceGroupIdAndProfileIdAndGroupPlacePlaceName(Long groupId, Long profileId, String placeName);

}
