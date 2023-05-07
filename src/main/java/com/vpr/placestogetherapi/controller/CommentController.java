package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.CommentPlace;
import com.vpr.placestogetherapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{groupId}/add/dgisid/{placeDgisId}/profile/{profileId}")
    public ResponseEntity<CommentPlace> addComment(@PathVariable Long groupId, @PathVariable Long placeDgisId, @PathVariable Long profileId, @RequestBody String commentText) {
        CommentPlace comment = commentService.addComment(profileId, groupId, placeDgisId, commentText);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/{groupId}/update/dgisid/{placeDgisId}/profile/{profileId}/comment/{commentId}")
    public ResponseEntity<CommentPlace> updateComment(@PathVariable Long groupId, @PathVariable Long placeDgisId, @PathVariable Long profileId, @PathVariable Long commentId, @RequestBody String newCommentText) {
        CommentPlace comment = commentService.updateComment(profileId, groupId, placeDgisId, commentId, newCommentText);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{groupId}/delete/dgisid/{placeDgisId}/profile/{editorProfileId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long groupId, @PathVariable Long placeDgisId, @PathVariable Long editorProfileId, @PathVariable Long commentId) {
        commentService.deleteComment(editorProfileId, groupId, placeDgisId, commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}/comments-by-profile/{profileId}")
    public ResponseEntity<List<CommentPlace>> getCommentsByGroupIdAndProfileId(@PathVariable Long groupId, @PathVariable Long profileId) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndProfileId(groupId, profileId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{groupId}/comments-by-dgisid/{dgisId}")
    public ResponseEntity<List<CommentPlace>> getCommentsByGroupIdAndDgisId(@PathVariable Long groupId, @PathVariable Long dgisId) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndDgisId(groupId, dgisId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{groupId}/comments-by-placename/{placeName}")
    public ResponseEntity<List<CommentPlace>> getCommentsByGroupIdAndPlaceName(@PathVariable Long groupId, @PathVariable String placeName) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndPlaceName(groupId, placeName);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{groupId}/comments-by-profile/{profileId}/by-dgisid/{dgisId}")
    public ResponseEntity<List<CommentPlace>> getCommentByGroupIdAndProfileIdAndDgisId(@PathVariable Long groupId, @PathVariable Long profileId, @PathVariable Long dgisId) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndProfileIdAndDgisId(groupId, profileId, dgisId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{groupId}/comments-by-profile/{profileId}/by-placename/{placeName}")
    public ResponseEntity<List<CommentPlace>> getCommentByGroupIdAndProfileIdAndPlaceName(@PathVariable Long groupId, @PathVariable Long profileId, @PathVariable String placeName) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndProfileIdAndPlaceName(groupId, profileId, placeName);
        return ResponseEntity.ok(comments);
    }
}