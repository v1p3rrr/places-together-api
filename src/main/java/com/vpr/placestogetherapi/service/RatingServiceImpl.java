package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.*;
import com.vpr.placestogetherapi.model.enums.GroupMemberRole;
import com.vpr.placestogetherapi.repository.RatingPlaceRepository;
import com.vpr.placestogetherapi.repository.GroupMembershipRepository;
import com.vpr.placestogetherapi.repository.GroupPlaceRepository;
import com.vpr.placestogetherapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingPlaceRepository ratingRepository;
    private final GroupPlaceRepository groupPlaceRepository;
    private final ProfileRepository profileRepository;
    private final GroupMembershipRepository groupMembershipRepository;

    @Override
    @Transactional
    public RatingPlace addRating(Long profileId, Long groupId, Long placeDgisId, Integer stars) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new NoSuchElementException("Profile not found"));
        GroupPlace groupPlace = groupPlaceRepository.findByGroupIdAndPlace_dgisId(groupId, placeDgisId).orElseThrow(() -> new NoSuchElementException("GroupPlace not found"));

        RatingPlace rating = new RatingPlace();
        rating.setGroupPlace(groupPlace);
        rating.setProfile(profile);
        rating.setStars(stars);

        return ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public RatingPlace updateRating(Long editorProfileId, Long groupId, Long placeDgisId, Long ratingId, Integer newStars) {
        Profile profile = profileRepository.findById(editorProfileId)
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));
        GroupPlace groupPlace = groupPlaceRepository.findByGroupIdAndPlace_dgisId(groupId, placeDgisId)
                .orElseThrow(() -> new NoSuchElementException("GroupPlace not found"));
        RatingPlace rating = ratingRepository.findByIdAndGroupPlace(ratingId, groupPlace)
                .orElseThrow(() -> new NoSuchElementException("Rating not found"));

        validateRatingEditor(profile, groupPlace.getGroup(), rating);

        rating.setStars(newStars);

        return ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public void deleteRating(Long editorProfileId, Long groupId, Long placeDgisId, Long ratingId) {
        Profile profile = profileRepository.findById(editorProfileId)
                .orElseThrow(() -> new NoSuchElementException("Editor's profile not found"));
        GroupPlace groupPlace = groupPlaceRepository.findByGroupIdAndPlace_dgisId(groupId, placeDgisId)
                .orElseThrow(() -> new NoSuchElementException("GroupPlace not found"));
        RatingPlace rating = ratingRepository.findByIdAndGroupPlace(ratingId, groupPlace)
                .orElseThrow(() -> new NoSuchElementException("Rating not found"));

        validateRatingEditor(profile, groupPlace.getGroup(), rating);

        ratingRepository.delete(rating);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingPlace> getRatingsByGroupIdAndProfileId(Long groupId, Long profileId) {
        return ratingRepository.findByGroupPlaceGroupIdAndProfileId(groupId, profileId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingPlace> getRatingsByGroupIdAndDgisId(Long groupId, Long dgisId) {
        return ratingRepository.findByGroupPlaceGroupIdAndGroupPlace_Place_dgisId(groupId, dgisId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingPlace> getRatingsByGroupIdAndPlaceName(Long groupId, String placeName) {
        return ratingRepository.findByGroupPlaceGroupIdAndGroupPlacePlaceName(groupId, placeName);
    }

    @Override
    @Transactional(readOnly = true)
    public RatingPlace getRatingByGroupIdAndProfileIdAndDgisId(Long groupId, Long profileId, Long dgisId) {
        return ratingRepository.findByGroupPlaceGroupIdAndProfileIdAndGroupPlace_Place_dgisId(groupId, profileId, dgisId)
                .orElseThrow(() -> new NoSuchElementException("Rating not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingPlace getRatingByGroupIdAndProfileIdAndPlaceName(Long groupId, Long profileId, String placeName) {
        return ratingRepository.findByGroupPlaceGroupIdAndProfileIdAndGroupPlacePlaceName(groupId, profileId, placeName)
                .orElseThrow(() -> new NoSuchElementException("Rating not found"));
    }

    private void validateRatingEditor(Profile profile, Group group, RatingPlace rating) {
        if (!rating.getProfile().getId().equals(profile.getId())) {
            GroupMembership membership = groupMembershipRepository.findByGroupAndProfile(group, profile)
                    .orElseThrow(() -> new NoSuchElementException("Membership not found"));
            if (membership.getRole() != GroupMemberRole.MODERATOR && membership.getRole() != GroupMemberRole.ADMIN) {
                throw new IllegalStateException("Only the owner or a moderator/admin can edit or delete the rating");
            }
        }
    }


}