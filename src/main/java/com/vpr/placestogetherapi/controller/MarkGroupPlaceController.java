package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.MarkPlace;
import com.vpr.placestogetherapi.model.entity.Place;
import com.vpr.placestogetherapi.model.enums.MarkPlaceStatus;
import com.vpr.placestogetherapi.service.MarkGroupPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marks")
@RequiredArgsConstructor
public class MarkGroupPlaceController {
    private final MarkGroupPlaceService markGroupPlaceService;

    @GetMapping("/group/{groupId}/profile/{profileId}")
    public ResponseEntity<List<MarkPlace>> getMarksByProfileIdAndGroupId(@PathVariable Long profileId, @PathVariable Long groupId) {
        List<MarkPlace> marks = markGroupPlaceService.getMarksByProfileIdAndGroupId(profileId, groupId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/group/{groupId}/dgisid/{dgisId}")
    public ResponseEntity<List<MarkPlace>> getMarksByGroupIdAndDgisId(@PathVariable Long groupId, @PathVariable Long dgisId) {
        List<MarkPlace> marks = markGroupPlaceService.getMarksByGroupIdAndDgisId(groupId, dgisId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/group/{groupId}/placename/{placeName}")
    public ResponseEntity<List<MarkPlace>> getMarksByGroupIdAndPlaceName(@PathVariable Long groupId, @PathVariable String placeName) {
        List<MarkPlace> marks = markGroupPlaceService.getMarksByGroupIdAndPlaceName(groupId, placeName);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/group/{groupId}/profile/{profileId}/placename/{placeName}")
    public ResponseEntity<MarkPlace> getMarksByGroupIdAndProfileIdAndPlaceName(@PathVariable Long groupId, @PathVariable Long profileId, @PathVariable String placeName) {
        MarkPlace mark = markGroupPlaceService.getMarksByGroupIdAndProfileIdAndPlaceName(groupId, profileId, placeName);
        return ResponseEntity.ok(mark);
    }

    @GetMapping("/group/{groupId}/profile/{profileId}/dgisid/{dgisId}")
    public ResponseEntity<MarkPlace> getMarksByGroupIdAndProfileIdAndDgisId(@PathVariable Long groupId, @PathVariable Long profileId, @PathVariable Long dgisId) {
        MarkPlace mark = markGroupPlaceService.getMarksByGroupIdAndProfileIdAndDgisId(groupId, profileId, dgisId);
        return ResponseEntity.ok(mark);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<MarkPlace>> getMarksByGroupId(@PathVariable Long groupId) {
        List<MarkPlace> marks = markGroupPlaceService.getMarksByGroupId(groupId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<MarkPlace> getMarkById(@PathVariable Long id) {
        MarkPlace mark = markGroupPlaceService.getMarkById(id);
        return ResponseEntity.ok(mark);
    }

    @PostMapping("/mark/{profileId}/{groupId}/{markStatus}")
    public ResponseEntity<MarkPlace> markPlace(@PathVariable Long profileId, @PathVariable Long groupId, @RequestBody Place place, @PathVariable String markStatus) {
        MarkPlace mark = markGroupPlaceService.markPlace(profileId, groupId, place, markStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(mark);
    }

    @PutMapping("/update/{placeDgisId}/{groupId}/{editorProfileId}/{targetProfileId}/{newStatus}")
    public ResponseEntity<MarkPlace> updateMark(@PathVariable Long placeDgisId, @PathVariable Long groupId, @PathVariable Long editorProfileId, @PathVariable Long targetProfileId, @PathVariable MarkPlaceStatus newStatus) {
        MarkPlace mark = markGroupPlaceService.updateMark(placeDgisId, groupId, editorProfileId, targetProfileId, newStatus);
        return ResponseEntity.ok(mark);
    }

    @DeleteMapping("/delete/{placeDgisId}/{groupId}/{editorProfileId}/{targetProfileId}")
    public ResponseEntity<Void> deleteMark(@PathVariable Long placeDgisId, @PathVariable Long groupId, @PathVariable Long editorProfileId, @PathVariable Long targetProfileId) {
        markGroupPlaceService.deleteMark(placeDgisId, groupId, editorProfileId, targetProfileId);
        return ResponseEntity.noContent().build();
    }
}