package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.GroupPlace;
import com.vpr.placestogetherapi.model.entity.MarkPlace;
import com.vpr.placestogetherapi.model.entity.Place;
import com.vpr.placestogetherapi.service.MarkGroupPlaceService;
import com.vpr.placestogetherapi.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
@Tag(name = "Place API", description = "APIs for getting information about places within or outside of groups")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/group/{groupId}/by-dgis-id/{dgisId}")
    @Operation(summary = "Get a group place by group ID and DGIS ID")
    public ResponseEntity<GroupPlace> getGroupPlaceByGroupIdAndDgisId(
            @Parameter(description = "The group ID") @PathVariable Long groupId,
            @Parameter(description = "The DGIS ID") @PathVariable Long dgisId) {
        GroupPlace groupPlace = placeService.getGroupPlaceByGroupIdAndDgisId(groupId, dgisId);
        return ResponseEntity.ok(groupPlace);
    }

    @GetMapping("/group/{groupId}/by-name/{name}")
    @Operation(summary = "Get a group place by name and group ID")
    public ResponseEntity<GroupPlace> getGroupPlaceByNameAndGroupId(
            @Parameter(description = "The name") @PathVariable String name,
            @Parameter(description = "The group ID") @PathVariable Long groupId) {
        GroupPlace groupPlace = placeService.getGroupPlaceByNameAndGroupId(name, groupId);
        return ResponseEntity.ok(groupPlace);
    }

    @GetMapping("/group/{groupId}/search-name/{partOfName}")
    @Operation(summary = "Get group places by part of name and group ID")
    public ResponseEntity<List<GroupPlace>> getGroupPlacesByPartOfNameAndGroupId(
            @Parameter(description = "The part of name") @PathVariable String partOfName,
            @Parameter(description = "The group ID") @PathVariable Long groupId) {
        List<GroupPlace> groupPlaces = placeService.getGroupPlacesByPartOfNameAndGroupId(partOfName, groupId);
        return ResponseEntity.ok(groupPlaces);
    }

    @GetMapping("/group/{groupId}/by-address/{address}")
    @Operation(summary = "Get group places by address and group ID")
    public ResponseEntity<List<GroupPlace>> getGroupPlacesByAddressAndGroupId(
            @Parameter(description = "The address") @PathVariable String address,
            @Parameter(description = "The group ID") @PathVariable Long groupId) {
        List<GroupPlace> groupPlaces = placeService.getGroupPlacesByAddressAndGroupId(address, groupId);
        return ResponseEntity.ok(groupPlaces);
    }

    @GetMapping("/group/{groupId}/by-type/{type}")
    @Operation(summary = "Get group places by group ID and type")
    public ResponseEntity<List<GroupPlace>> getGroupPlacesByGroupIdAndType(
            @Parameter(description = "The group ID") @PathVariable Long groupId,
            @Parameter(description = "The type") @PathVariable String type) {
        List<GroupPlace> groupPlaces = placeService.getGroupPlacesByGroupIdAndType(groupId, type);
        return ResponseEntity.ok(groupPlaces);
    }

    @GetMapping("/group/{groupId}/all")
    @Operation(summary = "Get all group places by group ID")
    public ResponseEntity<Set<GroupPlace>> getAllGroupPlacesByGroupId(
            @Parameter(description = "The group ID") @PathVariable Long groupId) {
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
    @Operation(summary = "Get a place by DGIS ID")
    public ResponseEntity<Place> getPlaceByDgisId(
            @Parameter(description = "The dgis id of the place") @PathVariable Long dgisId) {
        Place place = placeService.getPlaceByDgisId(dgisId);
        return ResponseEntity.ok(place);
    }

    @Operation(summary = "Get place by name")
    @GetMapping("/by-name/{name}")
    public ResponseEntity<Place> getPlaceByName(
            @Parameter(description = "The name of the place") @PathVariable String name) {
        Place place = placeService.getPlaceByName(name);
        return ResponseEntity.ok(place);
    }

    @Operation(summary = "Get places by part of name")
    @GetMapping("/search-name/{partOfName}")
    public ResponseEntity<List<Place>> getPlacesByPartOfName(
            @Parameter(description = "Part of the name of the place") @PathVariable String partOfName) {
        List<Place> places = placeService.getPlacesByPartOfName(partOfName);
        return ResponseEntity.ok(places);
    }

    @Operation(summary = "Get places by address")
    @GetMapping("/by-address/{address}")
    public ResponseEntity<List<Place>> getPlacesByAddress(
            @Parameter(description = "The address of the place") @PathVariable String address) {
        List<Place> places = placeService.getPlacesByAddress(address);
        return ResponseEntity.ok(places);
    }

    @Operation(summary = "Get places by type")
    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<Place>> getPlacesByType(
            @Parameter(description = "The type of the place") @PathVariable String type) {
        List<Place> places = placeService.getPlacesByType(type);
        return ResponseEntity.ok(places);
    }

    @Operation(summary = "Get all places")
    @GetMapping("/all")
    public ResponseEntity<List<Place>> getAllPlaces() {
        List<Place> places = placeService.getAllPlaces();
        return ResponseEntity.ok(places);
    }

}
