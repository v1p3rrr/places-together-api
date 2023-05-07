package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.GroupPlace;
import com.vpr.placestogetherapi.model.entity.Place;

import java.util.List;
import java.util.Set;

public interface PlaceService {
    Place getPlaceByDgisId(Long dgisId);
    GroupPlace getGroupPlaceByGroupIdAndDgisId(Long groupId, Long dgisId);
    Place getPlaceByName(String name);
    GroupPlace getGroupPlaceByNameAndGroupId(String name, Long groupId);
    List<Place> getPlacesByPartOfName(String partOfName);
    List<GroupPlace> getGroupPlacesByPartOfNameAndGroupId(String partOfName, Long groupId);
    List<Place> getPlacesByAddress(String address);
    List<GroupPlace> getGroupPlacesByAddressAndGroupId(String address, Long groupId);
    List<Place> getPlacesByType(String type);
    List<GroupPlace> getGroupPlacesByGroupIdAndType(Long groupId, String type);
    List<Place> getAllPlaces();
    Set<GroupPlace> getAllGroupPlacesByGroupId(Long groupId);
    Set<GroupPlace> getAllGroupPlacesByPlaceId(Long groupId);
}
