package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.model.enums.FriendshipStatus;

import java.util.Set;

public interface FriendshipService {
    Friendship requestFriendship(Long profileRequestId, Long profileAcceptId);
    Friendship acceptFriendship(Long profileRequestId, Long profileAcceptId);
    void removeFriendship(Long profileRequestId, Long profileAcceptId);
    public Friendship getFriendshipByProfileRequestIdOrProfileAcceptId(Long profileRequestId, Long profileAcceptId);
    public FriendshipStatus getFriendshipStatusByTwoProfilesId(Long profileId1, Long profileId2);
    public Set<Profile> getAllSentFriendshipRequestsProfiles(Long profileRequestId);
    public Set<Profile> getAllIncomingFriendshipRequestsProfiles(Long profileAcceptId);
    public Set<Profile> getAllConfirmedFriendsProfiles(Long profileId);
    public Boolean areFriends(Long profile1Id, Long profile2Id);
    public Boolean hasIncomingRequest(Long profileId, Long requestingSideProfileId);
    public Boolean hasOutcomingRequest(Long profileId, Long acceptingSideProfileId);
}
