package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.CommentPlace;

import java.util.List;

public interface CommentService {
    CommentPlace addComment(Long profileId, Long groupId, Long placeDgisId, String commentText);
    CommentPlace updateComment(Long profileId, Long groupId, Long placeDgisId, Long commentId, String newCommentText);
    void deleteComment(Long editorProfileId, Long groupId, Long placeDgisId, Long commentId);
    List<CommentPlace> getCommentsByGroupIdAndProfileId(Long groupId, Long profileId);
    List<CommentPlace> getCommentsByGroupIdAndDgisId(Long groupId, Long dgisId);
    List<CommentPlace> getCommentsByGroupIdAndPlaceName(Long groupId, String placeName);
    List<CommentPlace> getCommentsByGroupIdAndProfileIdAndDgisId(Long groupId, Long profileId, Long dgisId);
    List<CommentPlace> getCommentsByGroupIdAndProfileIdAndPlaceName(Long groupId, Long profileId, String placeName);

}
