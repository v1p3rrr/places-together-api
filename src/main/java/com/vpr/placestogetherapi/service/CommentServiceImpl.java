package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.*;
import com.vpr.placestogetherapi.model.enums.GroupMemberRole;
import com.vpr.placestogetherapi.repository.CommentPlaceRepository;
import com.vpr.placestogetherapi.repository.GroupMembershipRepository;
import com.vpr.placestogetherapi.repository.GroupPlaceRepository;
import com.vpr.placestogetherapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentPlaceRepository commentRepository;
    private final GroupPlaceRepository groupPlaceRepository;
    private final ProfileRepository profileRepository;
    private final GroupMembershipRepository groupMembershipRepository;

    @Override
    public CommentPlace addComment(Long profileId, Long groupId, Long placeDgisId, String commentText) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new NoSuchElementException("Profile not found"));
        GroupPlace groupPlace = groupPlaceRepository.findByGroupIdAndPlace_dgisId(groupId, placeDgisId).orElseThrow(() -> new NoSuchElementException("GroupPlace not found"));

        CommentPlace comment = new CommentPlace();
        comment.setGroupPlace(groupPlace);
        comment.setProfile(profile);
        comment.setCommentText(commentText);
        comment.setCreatedTimestamp(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Override
    public CommentPlace updateComment(Long profileId, Long groupId, Long placeDgisId, Long commentId, String newCommentText) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));
        GroupPlace groupPlace = groupPlaceRepository.findByGroupIdAndPlace_dgisId(groupId, placeDgisId)
                .orElseThrow(() -> new NoSuchElementException("GroupPlace not found"));
        CommentPlace comment = commentRepository.findByIdAndGroupPlace(commentId, groupPlace)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        validateCommentEditor(profile, groupPlace.getGroup(), comment);

        comment.setCommentText(newCommentText);

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long editorProfileId, Long groupId, Long placeDgisId, Long commentId) {
        Profile profile = profileRepository.findById(editorProfileId)
                .orElseThrow(() -> new NoSuchElementException("Editor's profile not found"));
        GroupPlace groupPlace = groupPlaceRepository.findByGroupIdAndPlace_dgisId(groupId, placeDgisId)
                .orElseThrow(() -> new NoSuchElementException("GroupPlace not found"));
        CommentPlace comment = commentRepository.findByIdAndGroupPlace(commentId, groupPlace)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        validateCommentEditor(profile, groupPlace.getGroup(), comment);

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentPlace> getCommentsByGroupIdAndProfileId(Long groupId, Long profileId) {
        return commentRepository.findByGroupPlaceGroupIdAndProfileId(groupId, profileId);
    }

    @Override
    public List<CommentPlace> getCommentsByGroupIdAndDgisId(Long groupId, Long dgisId) {
        return commentRepository.findByGroupPlaceGroupIdAndGroupPlace_Place_dgisId(groupId, dgisId);
    }

    @Override
    public List<CommentPlace> getCommentsByGroupIdAndPlaceName(Long groupId, String placeName) {
        return commentRepository.findByGroupPlaceGroupIdAndGroupPlacePlaceName(groupId, placeName);
    }

    @Override
    public List<CommentPlace> getCommentsByGroupIdAndProfileIdAndDgisId(Long groupId, Long profileId, Long dgisId) {
        return commentRepository.findByGroupPlaceGroupIdAndProfileIdAndGroupPlace_Place_dgisId(groupId, profileId, dgisId);
    }

    @Override
    public List<CommentPlace> getCommentsByGroupIdAndProfileIdAndPlaceName(Long groupId, Long profileId, String placeName) {
        return commentRepository.findByGroupPlaceGroupIdAndProfileIdAndGroupPlacePlaceName(groupId, profileId, placeName);
    }

    private void validateCommentEditor(Profile profile, Group group, CommentPlace comment) {
        if (!comment.getProfile().getId().equals(profile.getId())) {
            GroupMembership membership = groupMembershipRepository.findByGroupAndProfile(group, profile)
                    .orElseThrow(() -> new NoSuchElementException("Membership not found"));
            if (membership.getRole() != GroupMemberRole.MODERATOR && membership.getRole() != GroupMemberRole.ADMIN) {
                throw new IllegalStateException("Only the owner or a moderator/admin can edit or delete the comment");
            }
        }
    }

}