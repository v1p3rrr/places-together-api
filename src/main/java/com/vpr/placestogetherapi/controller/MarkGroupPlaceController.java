package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.MarkPlace;
import com.vpr.placestogetherapi.model.entity.Place;
import com.vpr.placestogetherapi.model.enums.MarkPlaceStatus;
import com.vpr.placestogetherapi.service.MarkGroupPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marks")
@RequiredArgsConstructor
@Tag(name = "Marks API", description = "APIs for managing marks of places within groups")
public class MarkGroupPlaceController {
    private final MarkGroupPlaceService markGroupPlaceService;

    @GetMapping("/group/{groupId}/profile/{profileId}")
    @Operation(summary = "Get all marks by profile id and group id")
    public ResponseEntity<List<MarkPlace>> getMarksByProfileIdAndGroupId(
            @Parameter(description = "The profile id") @PathVariable Long profileId,
            @Parameter(description = "The group id") @PathVariable Long groupId) {
        List<MarkPlace> marks = markGroupPlaceService.getMarksByProfileIdAndGroupId(profileId, groupId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/group/{groupId}/dgisid/{dgisId}")
    @Operation(summary = "Get all marks by group id and dgis id")
    public ResponseEntity<List<MarkPlace>> getMarksByGroupIdAndDgisId(
            @Parameter(description = "The group id") @PathVariable Long groupId,
            @Parameter(description = "The DGIS id") @PathVariable Long dgisId) {
        List<MarkPlace> marks = markGroupPlaceService.getMarksByGroupIdAndDgisId(groupId, dgisId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/group/{groupId}/placename/{placeName}")
    @Operation(summary = "Get all marks by group id and place name")
    public ResponseEntity<List<MarkPlace>> getMarksByGroupIdAndPlaceName(
            @Parameter(description = "The group id") @PathVariable Long groupId,
            @Parameter(description = "The place name") @PathVariable String placeName) {
        List<MarkPlace> marks = markGroupPlaceService.getMarksByGroupIdAndPlaceName(groupId, placeName);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/group/{groupId}/profile/{profileId}/placename/{placeName}")
    @Operation(summary = "Get a mark by group id, profile id, and place name")
    public ResponseEntity<MarkPlace> getMarksByGroupIdAndProfileIdAndPlaceName(
            @Parameter(description = "The group id") @PathVariable Long groupId,
            @Parameter(description = "The profile id") @PathVariable Long profileId,
            @Parameter(description = "The place name") @PathVariable String placeName) {
        MarkPlace mark = markGroupPlaceService.getMarksByGroupIdAndProfileIdAndPlaceName(groupId, profileId, placeName);
        return ResponseEntity.ok(mark);
    }

    @GetMapping("/group/{groupId}/profile/{profileId}/dgisid/{dgisId}")
    @Operation(summary = "Get a mark by group id, profile id, and DGIS id")
    public ResponseEntity<MarkPlace> getMarksByGroupIdAndProfileIdAndDgisId(
            @Parameter(description = "The group id") @PathVariable Long groupId,
            @Parameter(description = "The profile id") @PathVariable Long profileId,
            @Parameter(description = "The DGIS id") @PathVariable Long dgisId) {
        MarkPlace mark = markGroupPlaceService.getMarksByGroupIdAndProfileIdAndDgisId(groupId, profileId, dgisId);
        return ResponseEntity.ok(mark);
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get all marks by group id")
    public ResponseEntity<List<MarkPlace>> getMarksByGroupId(
            @Parameter(description = "The group id") @PathVariable Long groupId) {
        List<MarkPlace> marks = markGroupPlaceService.getMarksByGroupId(groupId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/by-id/{id}")
    @Operation(summary = "Get a mark by id")
    public ResponseEntity<MarkPlace> getMarkById(
            @Parameter(description = "The mark id") @PathVariable Long id) {
        MarkPlace mark = markGroupPlaceService.getMarkById(id);
        return ResponseEntity.ok(mark);
    }

    @PostMapping("/mark/{profileId}/{groupId}/{markStatus}")
    @Operation(summary = "Mark a place in a group")
    public ResponseEntity<MarkPlace> markPlace(
            @Parameter(description = "The profile id of the user marking the place") @PathVariable Long profileId,
            @Parameter(description = "The group id where the place is being marked") @PathVariable Long groupId,
            @RequestBody Place place,
            @Parameter(description = "The status of the mark") @PathVariable String markStatus) {
        MarkPlace mark = markGroupPlaceService.markPlace(profileId, groupId, place, markStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(mark);
    }

    @PutMapping("/update/{placeDgisId}/{groupId}/{editorProfileId}/{targetProfileId}/{newStatus}")
    @Operation(summary = "Update a mark")
    public ResponseEntity<MarkPlace> updateMark(
            @Parameter(description = "The dgis id of the place being marked") @PathVariable Long placeDgisId,
            @Parameter(description = "The group id where the place is marked") @PathVariable Long groupId,
            @Parameter(description = "The profile id of the user editing the mark") @PathVariable Long editorProfileId,
            @Parameter(description = "The profile id of the user whose mark is being edited") @PathVariable Long targetProfileId,
            @Parameter(description = "The new status of the mark") @PathVariable MarkPlaceStatus newStatus) {
        MarkPlace mark = markGroupPlaceService.updateMark(placeDgisId, groupId, editorProfileId, targetProfileId, newStatus);
        return ResponseEntity.ok(mark);
    }

    @DeleteMapping("/delete/{placeDgisId}/{groupId}/{editorProfileId}/{targetProfileId}")
    @Operation(summary = "Delete a mark")
    public ResponseEntity<Void> deleteMark(
            @Parameter(description = "The dgis id of the place being marked") @PathVariable Long placeDgisId,
            @Parameter(description = "The group id where the place is marked") @PathVariable Long groupId,
            @Parameter(description = "The profile id of the user deleting the mark") @PathVariable Long editorProfileId,
            @Parameter(description = "The profile id of the user whose mark is being deleted") @PathVariable Long targetProfileId) {
        markGroupPlaceService.deleteMark(placeDgisId, groupId, editorProfileId, targetProfileId);
        return ResponseEntity.noContent().build();
    }
}