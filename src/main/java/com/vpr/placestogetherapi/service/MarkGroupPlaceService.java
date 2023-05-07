package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.MarkPlace;
import com.vpr.placestogetherapi.model.entity.Place;
import com.vpr.placestogetherapi.model.enums.MarkPlaceStatus;

import java.util.List;

public interface MarkGroupPlaceService {
    MarkPlace markPlace(Long profileId, Long groupId, Place place, String markStatus);

    MarkPlace updateMark(Long placeDgisId, Long groupId, Long editorProfileId, Long targetProfileId, MarkPlaceStatus newStatus);

    void deleteMark(Long placeDgisId, Long groupId, Long editorProfileId, Long targetProfileId);

    List<MarkPlace> getMarksByProfileIdAndGroupId(Long profileId, Long groupId);

    List<MarkPlace> getMarksByGroupIdAndDgisId(Long profileId, Long dgisId);

    List<MarkPlace> getMarksByGroupIdAndPlaceName(Long profileId, String placeName);

    List<MarkPlace> getMarksByGroupId(Long groupId);

    MarkPlace getMarksByGroupIdAndProfileIdAndPlaceName(Long groupId, Long profileId, String placeName);

    MarkPlace getMarksByGroupIdAndProfileIdAndDgisId(Long groupId, Long profileId, Long dgisId);

    MarkPlace getMarkById(Long id);
}