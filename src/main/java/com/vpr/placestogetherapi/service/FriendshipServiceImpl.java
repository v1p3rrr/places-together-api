package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.model.enums.FriendshipStatus;
import com.vpr.placestogetherapi.repository.FriendshipRepository;
import com.vpr.placestogetherapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final ProfileRepository profileRepository;

    @Override
    public Friendship requestFriendship(Long profileRequestId, Long profileAcceptId) {
        if (profileRequestId.equals(profileAcceptId)) {
            throw new IllegalArgumentException("A profile cannot have a friendship with itself.");
        }

        Profile profileRequest = profileRepository.findById(profileRequestId)
                .orElseThrow(() -> new NoSuchElementException("ProfileRequest not found"));
        Profile profileAccept = profileRepository.findById(profileAcceptId)
                .orElseThrow(() -> new NoSuchElementException("ProfileAccept not found"));

        // Check if a friendship already exists
        Optional<Friendship> existingFriendship1 = friendshipRepository.findByProfileRequestAndProfileAccept(profileRequest, profileAccept);
        Optional<Friendship> existingFriendship2 = friendshipRepository.findByProfileRequestAndProfileAccept(profileRequest, profileAccept);

        if (existingFriendship1.isPresent() || existingFriendship2.isPresent()) {
            throw new IllegalStateException("A friendship already exists between these profiles.");
        }

        // Create a new friendship
        Friendship friendship = new Friendship();
        friendship.setProfileRequest(profileRequest);
        friendship.setProfileAccept(profileAccept);
        friendship.setStatus(FriendshipStatus.REQUESTED);

        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship acceptFriendship(Long profileRequestId, Long profileAcceptId) {
        Profile profileRequest = profileRepository.findById(profileRequestId)
                .orElseThrow(() -> new NoSuchElementException("ProfileRequest not found"));
        Profile profileAccept = profileRepository.findById(profileAcceptId)
                .orElseThrow(() -> new NoSuchElementException("ProfileAccept not found"));


        Optional<Friendship> existingFriendship = friendshipRepository.findByProfileRequestAndProfileAccept(profileRequest, profileAccept);
        if (existingFriendship.isEmpty()) {
            existingFriendship = friendshipRepository.findByProfileRequestAndProfileAccept(profileAccept, profileRequest);
            if (existingFriendship.isEmpty()) {
                throw new IllegalStateException("There wasn't such request between these profiles");
            }
        }
        Friendship updatedFriendship = existingFriendship.get();

        if (updatedFriendship.getStatus() != FriendshipStatus.REQUESTED) {
            throw new IllegalStateException("Friendship between these profiles isn't requested");
        }

        updatedFriendship.setStatus(FriendshipStatus.FRIENDS);

        return friendshipRepository.save(updatedFriendship);
    }

    @Override
    public void removeFriendship(Long profileRequestId, Long profileAcceptId) {
        Profile profileRequest = profileRepository.findById(profileRequestId)
                .orElseThrow(() -> new NoSuchElementException("ProfileRequest not found"));
        Profile profileAccept = profileRepository.findById(profileAcceptId)
                .orElseThrow(() -> new NoSuchElementException("ProfileAccept not found"));

        Optional<Friendship> existingFriendship = friendshipRepository.findByProfileRequestAndProfileAccept(profileRequest, profileAccept);
        if (existingFriendship.isEmpty()) {
            existingFriendship = friendshipRepository.findByProfileRequestAndProfileAccept(profileAccept, profileRequest);
            if (existingFriendship.isEmpty()) {
                throw new IllegalStateException("There wasn't such friendship between these profiles");
            }
        }
        Friendship updatedFriendship = existingFriendship.get();

        friendshipRepository.delete(updatedFriendship);
    }

    @Override
    public Friendship getFriendshipByProfileRequestIdOrProfileAcceptId(Long profileRequestId, Long profileAcceptId) {
        Optional<Friendship> friendship = friendshipRepository.findByProfileRequestIdAndProfileAcceptId(profileRequestId, profileAcceptId);
        return friendship.orElseGet(() -> friendshipRepository.findByProfileRequestIdAndProfileAcceptId(profileAcceptId, profileRequestId)
                .orElseThrow(() -> new NoSuchElementException("Friendship between profile with id " + profileRequestId + " and profile with id " + profileAcceptId + " was not found")));
    }

    //useless
    public Set<Friendship> getFriendshipByProfileRequestId(Long profileRequestId) {
        return friendshipRepository.findByProfileRequestId(profileRequestId);
    }

    //useless
    public Set<Friendship> getFriendshipByProfileAcceptId(Long profileAcceptId) {
        return friendshipRepository.findByProfileAcceptId(profileAcceptId);
    }

    @Override
    public FriendshipStatus getFriendshipStatusByTwoProfilesId(Long profileId1, Long profileId2){
        FriendshipStatus status = friendshipRepository.findStatusByProfileRequestIdAndProfileAcceptId(profileId1, profileId2);
        if (status==null){
            status = friendshipRepository.findStatusByProfileRequestIdAndProfileAcceptId(profileId2, profileId1);
        }
        return status; //whether it's null or not
    }

    // useless
    public Set<Profile> getAllFriendsRequestedProfilesAndStatus(Long profileRequestId, FriendshipStatus friendshipStatus) {
        return profileRepository.findByFriendshipRequestProfileRequestIdAndFriendshipRequestStatus(profileRequestId, friendshipStatus);
    }

    @Override
    public Set<Profile> getAllSentFriendshipRequestsProfiles(Long profileRequestId) {
        return profileRepository.findByFriendshipRequestProfileRequestIdAndFriendshipRequestStatus(profileRequestId, FriendshipStatus.REQUESTED);
    }

    // useless
    public Set<Profile> getAllFriendsProfilesByProfileAcceptIdAndStatus(Long profileAcceptId, FriendshipStatus friendshipStatus) {
        return profileRepository.findByFriendshipAcceptProfileAcceptIdAndFriendshipAcceptStatus(profileAcceptId, friendshipStatus);
    }

    @Override
    public Set<Profile> getAllIncomingFriendshipRequestsProfiles(Long profileAcceptId) {
        return profileRepository.findByFriendshipAcceptProfileAcceptIdAndFriendshipAcceptStatus(profileAcceptId, FriendshipStatus.REQUESTED);
    }

    // useless
    public Set<Profile> getAllFriendsProfilesByProfileId(Long profileId) {
        return profileRepository.findByFriendshipRequestProfileRequestIdOrFriendshipAcceptProfileAcceptId(profileId, profileId);

    }

    @Override
    public Set<Profile> getAllConfirmedFriendsProfiles(Long profileId){
        return profileRepository.findByFriendshipRequestProfileRequestIdAndFriendshipRequestStatusOrFriendshipAcceptProfileAcceptIdAndFriendshipAcceptStatus(profileId, FriendshipStatus.FRIENDS, profileId, FriendshipStatus.FRIENDS);
    }

    @Override
    public Boolean areFriends(Long profile1Id, Long profile2Id){
        return profileRepository.existsByIdAndFriendshipRequestStatusAndFriendshipRequestProfileRequestIdOrIdAndFriendshipAcceptStatusAndFriendshipAcceptProfileAcceptId(profile1Id, FriendshipStatus.FRIENDS, profile2Id, profile1Id, FriendshipStatus.FRIENDS, profile2Id);
    }

    @Override
    public Boolean hasIncomingRequest(Long profileId, Long requestingSideProfileId){
        return profileRepository.existsByIdAndFriendshipRequestStatusAndFriendshipRequestProfileRequestId(profileId, FriendshipStatus.REQUESTED, requestingSideProfileId);
    }

    @Override
    public Boolean hasOutcomingRequest(Long profileId, Long acceptingSideProfileId){
        return profileRepository.existsByIdAndFriendshipAcceptStatusAndFriendshipAcceptProfileAcceptId(profileId, FriendshipStatus.REQUESTED, acceptingSideProfileId);
    }

}