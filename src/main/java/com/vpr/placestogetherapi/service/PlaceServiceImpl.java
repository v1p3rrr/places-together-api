package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.GroupPlace;
import com.vpr.placestogetherapi.model.entity.Place;
import com.vpr.placestogetherapi.repository.GroupPlaceRepository;
import com.vpr.placestogetherapi.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    private final GroupPlaceRepository groupPlaceRepository;

    @Override
    public Place getPlaceByDgisId(Long dgisId) {
        return placeRepository.findByDgisId(dgisId)
                .orElseThrow(() -> new NoSuchElementException("Place not found with dgisId: " + dgisId));
    }

    @Override
    public Place getPlaceByName(String name) {
        return placeRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Place not found with name: " + name));
    }

    @Override
    public List<Place> getPlacesByPartOfName(String partOfName) {
        return placeRepository.findByNameContaining(partOfName);
    }

    @Override
    public List<Place> getPlacesByAddress(String address) {
        return placeRepository.findByAddress(address);
    }

    @Override
    public List<Place> getPlacesByType(String type) {
        return placeRepository.findByType(type);
    }

    @Override
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    @Override
    public GroupPlace getGroupPlaceByGroupIdAndDgisId(Long groupId, Long dgisId) {
        return groupPlaceRepository.findByGroupIdAndPlace_dgisId(groupId, dgisId)
                .orElseThrow(() -> new NoSuchElementException("GroupPlace not found with dgisId: " + dgisId + " and groupId: " + groupId));
    }

    @Override
    public GroupPlace getGroupPlaceByNameAndGroupId(String name, Long groupId) {
        return groupPlaceRepository.findByGroupIdAndPlaceName(groupId, name)
                .orElseThrow(() -> new NoSuchElementException("GroupPlace not found with name: " + name + " and groupId: " + groupId));
    }

    @Override
    public List<GroupPlace> getGroupPlacesByPartOfNameAndGroupId(String partOfName, Long groupId) {
        return groupPlaceRepository.findByGroupIdAndPlaceNameContaining(groupId, partOfName);
    }

    @Override
    public List<GroupPlace> getGroupPlacesByAddressAndGroupId(String address, Long groupId) {
        return groupPlaceRepository.findByGroupIdAndPlaceAddress(groupId, address);
    }

    @Override
    public List<GroupPlace> getGroupPlacesByGroupIdAndType(Long groupId, String type) {
        return groupPlaceRepository.findByGroupIdAndPlaceType(groupId, type);
    }

    @Override
    public Set<GroupPlace> getAllGroupPlacesByGroupId(Long groupId) {
        return groupPlaceRepository.findByGroupId(groupId);
    }

    @Override
    public Set<GroupPlace> getAllGroupPlacesByPlaceId(Long groupId) {
        return groupPlaceRepository.findByPlaceId(groupId);
    }


}
