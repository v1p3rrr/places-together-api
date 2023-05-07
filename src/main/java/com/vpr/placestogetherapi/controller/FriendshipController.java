package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.model.enums.FriendshipStatus;
import com.vpr.placestogetherapi.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/friendship")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @GetMapping("/{profileRequestId}/{profileAcceptId}/request-friendship")
    public ResponseEntity<Friendship> requestFriendship(@PathVariable Long profileRequestId, @PathVariable Long profileAcceptId) {
        Friendship friendship = friendshipService.requestFriendship(profileRequestId, profileAcceptId);
        return ResponseEntity.status(HttpStatus.CREATED).body(friendship);
    }

    @GetMapping("/{profileRequestId}/{profileAcceptId}/accept-friendship")
    public ResponseEntity<Friendship> acceptFriendship(@PathVariable Long profileRequestId, @PathVariable Long profileAcceptId){
        Friendship friendship = friendshipService.acceptFriendship(profileRequestId, profileAcceptId);
        return ResponseEntity.ok(friendship);
    }

    @DeleteMapping("/{profileRequestId}/{profileAcceptId}/remove-friendship")
    public ResponseEntity<Void> removeFriendship(@PathVariable Long profileRequestId, @PathVariable Long profileAcceptId) {
        friendshipService.removeFriendship(profileRequestId, profileAcceptId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{profileRequestId}/friendship/{profileAcceptId}")
    public ResponseEntity<Friendship> getFriendshipByProfileRequestIdOrProfileAcceptId(@PathVariable Long profileRequestId, @PathVariable Long profileAcceptId) {
        Friendship friendship = friendshipService.getFriendshipByProfileRequestIdOrProfileAcceptId(profileRequestId, profileAcceptId);
        return ResponseEntity.ok(friendship);
    }

    @GetMapping("/{profileId1}/friendship-status/{profileId2}")
    public ResponseEntity<FriendshipStatus> getFriendshipStatusByTwoProfilesId(@PathVariable Long profileId1, @PathVariable Long profileId2) {
        FriendshipStatus friendshipStatus = friendshipService.getFriendshipStatusByTwoProfilesId(profileId1, profileId2);
        return ResponseEntity.ok(friendshipStatus);
    }

    @GetMapping("/{profileRequestId}/sent-requests")
    public ResponseEntity<Set<Profile>> getAllSentFriendshipRequestsProfiles(@PathVariable Long profileRequestId) {
        Set<Profile> sentRequests = friendshipService.getAllSentFriendshipRequestsProfiles(profileRequestId);
        return ResponseEntity.ok(sentRequests);
    }

    @GetMapping("/{profileAcceptId}/incoming-requests")
    public ResponseEntity<Set<Profile>> getAllIncomingFriendshipRequestsProfiles(@PathVariable Long profileAcceptId) {
        Set<Profile> incomingRequests = friendshipService.getAllIncomingFriendshipRequestsProfiles(profileAcceptId);
        return ResponseEntity.ok(incomingRequests);
    }

    @GetMapping("/{profileId}/confirmed-friends")
    public ResponseEntity<Set<Profile>> getAllConfirmedFriendsProfiles(@PathVariable Long profileId) {
        Set<Profile> confirmedFriends = friendshipService.getAllConfirmedFriendsProfiles(profileId);
        return ResponseEntity.ok(confirmedFriends);
    }

    @GetMapping("/{profile1Id}/are-friends/{profile2Id}")
    public ResponseEntity<Boolean> areFriends(@PathVariable Long profile1Id, @PathVariable Long profile2Id) {
        Boolean areFriends = friendshipService.areFriends(profile1Id, profile2Id);
        return ResponseEntity.ok(areFriends);
    }

    @GetMapping("/{profileId}/has-incoming-request/{requestingSideProfileId}")
    public ResponseEntity<Boolean> hasIncomingRequest(@PathVariable Long profileId, @PathVariable Long requestingSideProfileId) {
        Boolean hasIncomingRequest = friendshipService.hasIncomingRequest(profileId, requestingSideProfileId);
        return ResponseEntity.ok(hasIncomingRequest);
    }

    @GetMapping("/{profileId}/has-outcoming-request/{acceptingSideProfileId}")
    public ResponseEntity<Boolean> hasOutcomingRequest(@PathVariable Long profileId, @PathVariable Long acceptingSideProfileId) {
        Boolean hasOutcomingRequest = friendshipService.hasOutcomingRequest(profileId, acceptingSideProfileId);
        return ResponseEntity.ok(hasOutcomingRequest);
    }
}
