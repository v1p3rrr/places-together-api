package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.model.enums.FriendshipStatus;
import com.vpr.placestogetherapi.service.FriendshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/friendship")
@RequiredArgsConstructor
@Tag(name = "Friendship API", description = "APIs for managing friendship between users")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/{profileRequestId}/{profileAcceptId}/request-friendship")
    @Operation(summary = "Request friendship")
    public ResponseEntity<Friendship> requestFriendship(
            @Parameter(description = "Profile ID of the user sending the request", required = true)
            @PathVariable Long profileRequestId,
            @Parameter(description = "Profile ID of the user accepting the request", required = true)
            @PathVariable Long profileAcceptId) {
        Friendship friendship = friendshipService.requestFriendship(profileRequestId, profileAcceptId);
        return ResponseEntity.status(HttpStatus.CREATED).body(friendship);
    }

    @PostMapping("/{profileRequestId}/{profileAcceptId}/accept-friendship")
    @Operation(summary = "Accept friendship request")
    public ResponseEntity<Friendship> acceptFriendship(
            @Parameter(description = "Profile ID of the user who sent the request", required = true)
            @PathVariable Long profileRequestId,
            @Parameter(description = "Profile ID of the user who accepted the request", required = true)
            @PathVariable Long profileAcceptId){
        Friendship friendship = friendshipService.acceptFriendship(profileRequestId, profileAcceptId);
        return ResponseEntity.ok(friendship);
    }

    @DeleteMapping("/{profileRequestId}/{profileAcceptId}/remove-friendship")
    @Operation(summary = "Remove friendship")
    public ResponseEntity<Void> removeFriendship(
            @Parameter(description = "Profile ID of the user who sent the request", required = true)
            @PathVariable Long profileRequestId,
            @Parameter(description = "Profile ID of the user who accepted the request", required = true)
            @PathVariable Long profileAcceptId) {
        friendshipService.removeFriendship(profileRequestId, profileAcceptId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{profileRequestId}/friendship/{profileAcceptId}")
    @Operation(summary = "Get friendship by profile request or profile accept ID")
    public ResponseEntity<Friendship> getFriendshipByProfileRequestIdOrProfileAcceptId(
            @Parameter(description = "Profile ID of the user who sent the request or accepted the request", required = true)
            @PathVariable Long profileRequestId,
            @Parameter(description = "Profile ID of the user who accepted the request or sent the request", required = true)
            @PathVariable Long profileAcceptId) {
        Friendship friendship = friendshipService.getFriendshipByProfileRequestIdOrProfileAcceptId(profileRequestId, profileAcceptId);
        return ResponseEntity.ok(friendship);
    }

    @GetMapping("/{profileId1}/friendship-status/{profileId2}")
    @Operation(summary = "Get friendship status by two profiles ID")
    public ResponseEntity<FriendshipStatus> getFriendshipStatusByTwoProfilesId(
            @Parameter(description = "Profile ID of the first user", required = true)
            @PathVariable Long profileId1,
            @Parameter(description = "Profile ID of the second user", required = true)
            @PathVariable Long profileId2) {
        FriendshipStatus friendshipStatus = friendshipService.getFriendshipStatusByTwoProfilesId(profileId1, profileId2);
        return ResponseEntity.ok(friendshipStatus);
    }

    @GetMapping("/{profileRequestId}/sent-requests")
    @Operation(summary = "Get all sent friendship requests profiles")
    public ResponseEntity<Set<Profile>> getAllSentFriendshipRequestsProfiles(
            @Parameter(description = "Profile ID of the user who sent the requests", required = true)
            @PathVariable Long profileRequestId) {
        Set<Profile> sentRequests = friendshipService.getAllSentFriendshipRequestsProfiles(profileRequestId);
        return ResponseEntity.ok(sentRequests);
    }

    @GetMapping("/{profileAcceptId}/incoming-requests")
    @Operation(summary = "Get all incoming friendship requests for a certain profile")
    public ResponseEntity<Set<Profile>> getAllIncomingFriendshipRequestsProfiles(
            @Parameter(description = "Profile ID", required = true) @PathVariable Long profileAcceptId) {
        Set<Profile> incomingRequests = friendshipService.getAllIncomingFriendshipRequestsProfiles(profileAcceptId);
        return ResponseEntity.ok(incomingRequests);
    }

    @GetMapping("/{profileId}/confirmed-friends")
    @Operation(summary = "Get all confirmed friends for a certain profile")
    public ResponseEntity<Set<Profile>> getAllConfirmedFriendsProfiles(
            @Parameter(description = "Profile ID", required = true) @PathVariable Long profileId) {
        Set<Profile> confirmedFriends = friendshipService.getAllConfirmedFriendsProfiles(profileId);
        return ResponseEntity.ok(confirmedFriends);
    }

    @GetMapping("/{profile1Id}/are-friends/{profile2Id}")
    @Operation(summary = "Check if two profiles are friends")
    public ResponseEntity<Boolean> areFriends(
            @Parameter(description = "Profile ID 1", required = true) @PathVariable Long profile1Id,
            @Parameter(description = "Profile ID 2", required = true) @PathVariable Long profile2Id) {
        Boolean areFriends = friendshipService.areFriends(profile1Id, profile2Id);
        return ResponseEntity.ok(areFriends);
    }

    @GetMapping("/{profileId}/has-incoming-request/{requestingSideProfileId}")
    @Operation(summary = "Check if a certain profile has an incoming friendship request from another profile")
    public ResponseEntity<Boolean> hasIncomingRequest(
            @Parameter(description = "Profile ID", required = true) @PathVariable Long profileId,
            @Parameter(description = "Requesting Profile ID", required = true) @PathVariable Long requestingSideProfileId) {
        Boolean hasIncomingRequest = friendshipService.hasIncomingRequest(profileId, requestingSideProfileId);
        return ResponseEntity.ok(hasIncomingRequest);
    }

    @GetMapping("/{profileId}/has-outcoming-request/{acceptingSideProfileId}")
    @Operation(summary = "Check if a certain profile has sent a friendship request to another profile")
    public ResponseEntity<Boolean> hasOutcomingRequest(
            @Parameter(description = "Profile ID", required = true) @PathVariable Long profileId,
            @Parameter(description = "Accepting Profile ID", required = true) @PathVariable Long acceptingSideProfileId) {
        Boolean hasOutcomingRequest = friendshipService.hasOutcomingRequest(profileId, acceptingSideProfileId);
        return ResponseEntity.ok(hasOutcomingRequest);
    }
}
