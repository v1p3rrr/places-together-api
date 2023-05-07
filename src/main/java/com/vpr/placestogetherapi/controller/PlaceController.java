package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.GroupPlace;
import com.vpr.placestogetherapi.model.entity.MarkPlace;
import com.vpr.placestogetherapi.model.entity.Place;
import com.vpr.placestogetherapi.service.MarkGroupPlaceService;
import com.vpr.placestogetherapi.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/group/{groupId}/by-dgis-id/{dgisId}")
    public ResponseEntity<GroupPlace> getGroupPlaceByGroupIdAndDgisId(@PathVariable Long groupId, @PathVariable Long dgisId) {
        GroupPlace groupPlace = placeService.getGroupPlaceByGroupIdAndDgisId(groupId, dgisId);
        return ResponseEntity.ok(groupPlace);
    }

    @GetMapping("/group/{groupId}/by-name/{name}")
    public ResponseEntity<GroupPlace> getGroupPlaceByNameAndGroupId(@PathVariable String name, @PathVariable Long groupId) {
        GroupPlace groupPlace = placeService.getGroupPlaceByNameAndGroupId(name, groupId);
        return ResponseEntity.ok(groupPlace);
    }

    @GetMapping("/group/{groupId}/search-name/{partOfName}")
    public ResponseEntity<List<GroupPlace>> getGroupPlacesByPartOfNameAndGroupId(@PathVariable String partOfName, @PathVariable Long groupId) {
        List<GroupPlace> groupPlaces = placeService.getGroupPlacesByPartOfNameAndGroupId(partOfName, groupId);
        return ResponseEntity.ok(groupPlaces);
    }

    @GetMapping("/group/{groupId}/by-address/{address}")
    public ResponseEntity<List<GroupPlace>> getGroupPlacesByAddressAndGroupId(@PathVariable String address, @PathVariable Long groupId) {
        List<GroupPlace> groupPlaces = placeService.getGroupPlacesByAddressAndGroupId(address, groupId);
        return ResponseEntity.ok(groupPlaces);
    }

    @GetMapping("/group/{groupId}/by-type/{type}")
    public ResponseEntity<List<GroupPlace>> getGroupPlacesByGroupIdAndType(@PathVariable Long groupId, @PathVariable String type) {
        List<GroupPlace> groupPlaces = placeService.getGroupPlacesByGroupIdAndType(groupId, type);
        return ResponseEntity.ok(groupPlaces);
    }

    @GetMapping("/group/{groupId}/all")
    public ResponseEntity<Set<GroupPlace>> getAllGroupPlacesByGroupId(@PathVariable Long groupId) {
        Set<GroupPlace> groupPlaces = placeService.getAllGroupPlacesByGroupId(groupId);
        return ResponseEntity.ok(groupPlaces);
    }



    //do not use in client, rather debug endpoints
    @GetMapping("/by-place-id/{placeId}/all-group-places")
    public ResponseEntity<Set<GroupPlace>> getAllGroupPlacesByPlaceId(@PathVariable Long placeId) {
        Set<GroupPlace> groupPlaces = placeService.getAllGroupPlacesByPlaceId(placeId);
        return ResponseEntity.ok(groupPlaces);
    }

    @GetMapping("/by-dgis-id/{dgisId}")
    public ResponseEntity<Place> getPlaceByDgisId(@PathVariable Long dgisId) {
        Place place = placeService.getPlaceByDgisId(dgisId);
        return ResponseEntity.ok(place);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<Place> getPlaceByName(@PathVariable String name) {
        Place place = placeService.getPlaceByName(name);
        return ResponseEntity.ok(place);
    }

    @GetMapping("/search-name/{partOfName}")
    public ResponseEntity<List<Place>> getPlacesByPartOfName(@PathVariable String partOfName) {
        List<Place> places = placeService.getPlacesByPartOfName(partOfName);
        return ResponseEntity.ok(places);
    }

    @GetMapping("/by-address/{address}")
    public ResponseEntity<List<Place>> getPlacesByAddress(@PathVariable String address) {
        List<Place> places = placeService.getPlacesByAddress(address);
        return ResponseEntity.ok(places);
    }

    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<Place>> getPlacesByType(@PathVariable String type) {
        List<Place> places = placeService.getPlacesByType(type);
        return ResponseEntity.ok(places);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Place>> getAllPlaces() {
        List<Place> places = placeService.getAllPlaces();
        return ResponseEntity.ok(places);
    }

}
