package com.vpr.placestogetherapi.repository;

import com.vpr.placestogetherapi.model.entity.Group;
import com.vpr.placestogetherapi.model.entity.GroupPlace;
import com.vpr.placestogetherapi.model.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupPlaceRepository extends JpaRepository<GroupPlace, Long> {
    Optional<GroupPlace> findByGroupAndPlace(Group group, Place place);
    Optional<GroupPlace> findByGroupIdAndPlace_dgisId(Long groupId, Long dgisId);
    Optional<GroupPlace> findByGroupIdAndPlaceName(Long groupId, String placeName);
    Set<GroupPlace> findByGroupId(Long groupId);
    Set<GroupPlace> findByPlaceId(Long placeId);
    List<GroupPlace> findByGroupIdAndPlaceAddress(Long groupId, String address);
    List<GroupPlace> findByGroupIdAndPlaceNameContaining(Long groupId, String partOfName);
    List<GroupPlace> findByGroupIdAndPlaceType(Long groupId, String type);

}
