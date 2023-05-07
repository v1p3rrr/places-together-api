package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.*;
import com.vpr.placestogetherapi.model.enums.GroupMemberRole;
import com.vpr.placestogetherapi.model.enums.MarkPlaceStatus;
import com.vpr.placestogetherapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarkGroupPlaceServiceImpl implements MarkGroupPlaceService {

    private final ProfileRepository profileRepository;
    private final GroupRepository groupRepository;
    private final PlaceRepository placeRepository;
    private final GroupPlaceRepository groupPlaceRepository;
    private final MarkPlaceRepository markRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    //todo get marks by Profile and Group, get all marks by group id, get mark by profile and groupplace(place 2gis id)
    @Override
    @Transactional
    public MarkPlace markPlace(Long profileId, Long groupId, Place place, String markStatus) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new NoSuchElementException("User's profile was not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        MarkPlaceStatus status = MarkPlaceStatus.valueOf(markStatus);

        Place savedPlace = placeRepository.findByDgisId(place.getDgisId()).orElseGet(() -> placeRepository.save(place));
        GroupPlace groupPlace = groupPlaceRepository.findByGroupAndPlace(group, savedPlace).orElseGet(() -> {
            GroupPlace newGroupPlace = new GroupPlace();
            newGroupPlace.setGroup(group);
            newGroupPlace.setPlace(savedPlace);
            return groupPlaceRepository.save(newGroupPlace);
        });

        Optional<MarkPlace> existingMarkPlace = markRepository.findByProfileAndGroupPlace(profile, groupPlace);
        if (existingMarkPlace.isPresent()) {
            MarkPlace updatedMarkPlace = existingMarkPlace.get();
            updatedMarkPlace.setStatus(status);
            return markRepository.save(updatedMarkPlace);
        } else {
            MarkPlace markPlace = new MarkPlace();
            markPlace.setProfile(profile);
            markPlace.setGroupPlace(groupPlace);
            markPlace.setStatus(status);
            return markRepository.save(markPlace);
        }
    }

    @Override
    @Transactional
    public MarkPlace updateMark(Long placeDgisId, Long groupId, Long editorProfileId, Long targetProfileId, MarkPlaceStatus newStatus) {
        Profile editorProfile = profileRepository.findById(editorProfileId)
                .orElseThrow(() -> new NoSuchElementException("Editor's profile not found"));

        Profile targetProfile = profileRepository.findById(targetProfileId)
                .orElseThrow(() -> new NoSuchElementException("Author's profile not found"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));

        Place place = placeRepository.findByDgisId(placeDgisId)
                .orElseThrow(() -> new NoSuchElementException("Place not found with dgisId: " + placeDgisId));

        GroupPlace groupPlace = groupPlaceRepository.findByGroupAndPlace(group, place)
                .orElseThrow(() -> new NoSuchElementException("GroupPlace not found for the specified group and place"));

        MarkPlace markPlace = markRepository.findByProfileAndGroupPlace(targetProfile, groupPlace)
                .orElseThrow(() -> new NoSuchElementException("MarkPlace not found for the specified profile and groupPlace"));

        validateMarkPlaceEditor(editorProfile, group, markPlace);

        markPlace.setStatus(newStatus);
        return markRepository.save(markPlace);
    }

    @Override
    @Transactional
    public void deleteMark(Long placeDgisId, Long groupId, Long editorProfileId, Long targetProfileId) {
        Profile editorProfile = profileRepository.findById(editorProfileId)
                .orElseThrow(() -> new NoSuchElementException("Editor profile not found with id: " + editorProfileId));

        Profile targetProfile = profileRepository.findById(targetProfileId)
                .orElseThrow(() -> new NoSuchElementException("Target profile not found with id: " + targetProfileId));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found with id: " + groupId));

        Place place = placeRepository.findByDgisId(placeDgisId)
                .orElseThrow(() -> new NoSuchElementException("Place not found with dgisId: " + placeDgisId));

        GroupPlace groupPlace = groupPlaceRepository.findByGroupAndPlace(group, place)
                .orElseThrow(() -> new NoSuchElementException("GroupPlace not found for the specified group and place"));

        MarkPlace markPlace = markRepository.findByProfileAndGroupPlace(targetProfile, groupPlace)
                .orElseThrow(() -> new NoSuchElementException("MarkPlace not found for the specified profile and groupPlace"));

        validateMarkPlaceEditor(editorProfile, group, markPlace);

        markRepository.delete(markPlace);

        if (markRepository.countByGroupPlace(groupPlace) == 0) {
            groupPlaceRepository.delete(groupPlace);
        }
    }

    @Override
    public List<MarkPlace> getMarksByProfileIdAndGroupId(Long profileId, Long groupId) {
        return markRepository.findByProfileIdAndGroupPlaceGroupId(profileId, groupId);
    }

    @Override
    public List<MarkPlace> getMarksByGroupIdAndDgisId(Long groupId, Long dgisId) {
        return markRepository.findByGroupPlaceGroupIdAndGroupPlace_Place_dgisId(groupId, dgisId);
    }

    @Override
    public List<MarkPlace> getMarksByGroupIdAndPlaceName(Long groupId, String placeName) {
        return markRepository.findByGroupPlaceGroupIdAndGroupPlacePlaceName(groupId, placeName);
    }

    @Override
    public List<MarkPlace> getMarksByGroupId(Long groupId) {
        return markRepository.findByGroupPlaceGroupId(groupId);
    }

    @Override
    public MarkPlace getMarksByGroupIdAndProfileIdAndPlaceName(Long groupId, Long profileId, String placeName) {
        return markRepository.findByGroupPlaceGroupIdAndProfileIdAndGroupPlacePlaceName(groupId, profileId, placeName)
                .orElseThrow(() -> new NoSuchElementException("Mark with was not found"));
    }

    @Override
    public MarkPlace getMarksByGroupIdAndProfileIdAndDgisId(Long groupId, Long profileId, Long dgisId) {
        return markRepository.findByGroupPlaceGroupIdAndProfileIdAndGroupPlace_Place_dgisId(groupId, profileId, dgisId)
                .orElseThrow(() -> new NoSuchElementException("Mark with was not found"));
    }

    @Override
    public MarkPlace getMarkById(Long id) {
        return markRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Mark with id " + id + " was not found"));
    }

    private void validateMarkPlaceEditor(Profile editorProfile, Group group, MarkPlace markPlace) {
        if (!markPlace.getProfile().equals(editorProfile)) {
            GroupMembership membership = groupMembershipRepository.findByGroupAndProfile(group, editorProfile)
                    .orElseThrow(() -> new NoSuchElementException("Membership not found for the editor profile and group"));
            if (membership.getRole() != GroupMemberRole.MODERATOR && membership.getRole() != GroupMemberRole.ADMIN) {
                throw new IllegalStateException("Only the owner or a moderator/admin can edit/delete the MarkPlace");
            }
        }
    }
}
