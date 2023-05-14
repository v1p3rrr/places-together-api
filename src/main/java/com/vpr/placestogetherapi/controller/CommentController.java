package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.CommentPlace;
import com.vpr.placestogetherapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comments API", description = "APIs for managing comments for places within groups")
public class CommentController {
    private final CommentService commentService;

        @Operation(summary = "Add comment")
        @PostMapping("/{groupId}/add/dgisid/{placeDgisId}/profile/{profileId}")
        public ResponseEntity<CommentPlace> addComment(
                @Parameter(description = "Group ID", required = true)
                @PathVariable Long groupId,
                @Parameter(description = "Place DGIS ID", required = true)
                @PathVariable Long placeDgisId,
                @Parameter(description = "Profile ID", required = true)
                @PathVariable Long profileId,
                @Parameter(description = "Comment text", required = true)
                @RequestBody String commentText) {
            CommentPlace comment = commentService.addComment(profileId, groupId, placeDgisId, commentText);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        }

        @Operation(summary = "Update comment")
        @PutMapping("/{groupId}/update/dgisid/{placeDgisId}/profile/{profileId}/comment/{commentId}")
        public ResponseEntity<CommentPlace> updateComment(
                @Parameter(description = "Group ID", required = true)
                @PathVariable Long groupId,
                @Parameter(description = "Place DGIS ID", required = true)
                @PathVariable Long placeDgisId,
                @Parameter(description = "Profile ID", required = true)
                @PathVariable Long profileId,
                @Parameter(description = "Comment ID", required = true)
                @PathVariable Long commentId,
                @Parameter(description = "New comment text", required = true)
                @RequestBody String newCommentText) {
            CommentPlace comment = commentService.updateComment(profileId, groupId, placeDgisId, commentId, newCommentText);
            return ResponseEntity.ok(comment);
        }

        @Operation(summary = "Delete comment")
        @DeleteMapping("/{groupId}/delete/dgisid/{placeDgisId}/profile/{editorProfileId}/comment/{commentId}")
        public ResponseEntity<Void> deleteComment(
                @Parameter(description = "Group ID", required = true)
                @PathVariable Long groupId,
                @Parameter(description = "Place DGIS ID", required = true)
                @PathVariable Long placeDgisId,
                @Parameter(description = "Editor profile ID", required = true)
                @PathVariable Long editorProfileId,
                @Parameter(description = "Comment ID", required = true)
                @PathVariable Long commentId) {
            commentService.deleteComment(editorProfileId, groupId, placeDgisId, commentId);
            return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Get comments by group ID and profile ID")
        @GetMapping("/{groupId}/comments-by-profile/{profileId}")
        public ResponseEntity<List<CommentPlace>> getCommentsByGroupIdAndProfileId(
                @Parameter(description = "Group ID", required = true)
                @PathVariable Long groupId,
                @Parameter(description = "Profile ID", required = true)
                @PathVariable Long profileId) {
            List<CommentPlace> comments = commentService.getCommentsByGroupIdAndProfileId(groupId, profileId);
            return ResponseEntity.ok(comments);
        }

        @Operation(summary = "Get comments by group ID and DGIS ID")
        @GetMapping("/{groupId}/comments-by-dgisid/{dgisId}")
        public ResponseEntity<List<CommentPlace>> getCommentsByGroupIdAndDgisId(
                @Parameter(description = "Group ID", required = true)
                @PathVariable Long groupId,
                @Parameter(description = "DGIS ID", required = true)
                @PathVariable Long dgisId) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndDgisId(groupId, dgisId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comments by group ID and place name")
    @GetMapping("/{groupId}/comments-by-placename/{placeName}")
    public ResponseEntity<List<CommentPlace>> getCommentsByGroupIdAndPlaceName(
            @Parameter(description = "Group ID", required = true)
            @PathVariable Long groupId,
            @Parameter(description = "Place name", required = true)
            @PathVariable String placeName) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndPlaceName(groupId, placeName);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comment by group ID, profile ID, and DGIS ID")
    @GetMapping("/{groupId}/comments-by-profile/{profileId}/by-dgisid/{dgisId}")
    public ResponseEntity<List<CommentPlace>> getCommentByGroupIdAndProfileIdAndDgisId(
            @Parameter(description = "Group ID", required = true)
            @PathVariable Long groupId,
            @Parameter(description = "Profile ID", required = true)
            @PathVariable Long profileId,
            @Parameter(description = "DGIS ID", required = true)
            @PathVariable Long dgisId) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndProfileIdAndDgisId(groupId, profileId, dgisId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comment by group ID, profile ID, and place name")
    @GetMapping("/{groupId}/comments-by-profile/{profileId}/by-placename/{placeName}")
    public ResponseEntity<List<CommentPlace>> getCommentByGroupIdAndProfileIdAndPlaceName(
            @Parameter(description = "Group ID", required = true)
            @PathVariable Long groupId,
            @Parameter(description = "Profile ID", required = true)
            @PathVariable Long profileId,
            @Parameter(description = "Place name", required = true)
            @PathVariable String placeName) {
        List<CommentPlace> comments = commentService.getCommentsByGroupIdAndProfileIdAndPlaceName(groupId, profileId, placeName);
        return ResponseEntity.ok(comments);
    }
}