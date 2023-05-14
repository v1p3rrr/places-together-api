package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.Group;
import com.vpr.placestogetherapi.model.entity.GroupMembership;
import com.vpr.placestogetherapi.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
@Tag(name = "Groups API", description = "APIs for managing groups and members")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/{groupId}")
    @Operation(summary = "Get a group by its ID")
    public ResponseEntity<Group> getGroup(@PathVariable @Parameter(description = "ID of the group") Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{groupId}/memberships")
    @Operation(summary = "Get all group memberships by group ID")
    public ResponseEntity<Set<GroupMembership>> getGroupMemberships(@PathVariable @Parameter(description = "ID of the group") Long groupId) {
        Set<GroupMembership> groupMemberships = groupService.getGroupMembershipsByGroupId(groupId);
        return ResponseEntity.ok(groupMemberships);
    }

    @PostMapping("/create/{adminProfileId}")
    @Operation(summary = "Create a new group")
    public ResponseEntity<Group> createGroup(@RequestBody @Parameter(description = "Name of the group") String groupName, @PathVariable @Parameter(description = "ID of the admin profile") Long adminProfileId) {
        Group group = groupService.createGroup(groupName, adminProfileId);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PostMapping("/{groupId}/invite/{invitedProfileId}")
    @Operation(summary = "Invite a profile to a group")
    public ResponseEntity<GroupMembership> inviteProfileToGroup(@PathVariable @Parameter(description = "ID of the group") Long groupId, @PathVariable @Parameter(description = "ID of the profile to be invited") Long invitedProfileId) {
        GroupMembership groupMembership = groupService.inviteProfileToGroup(groupId, invitedProfileId);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupMembership);
    }

    @PutMapping("/{groupId}/promote-moderator/{adminProfileId}/{promotedProfileId}")
    @Operation(summary = "Promote a member to moderator")
    public ResponseEntity<GroupMembership> promoteMemberToModerator(@PathVariable @Parameter(description = "ID of the group") Long groupId, @PathVariable @Parameter(description = "ID of the admin profile") Long adminProfileId, @PathVariable @Parameter(description = "ID of the profile to be promoted to moderator") Long promotedProfileId) {
        GroupMembership groupMembership = groupService.promoteMemberToModerator(groupId, adminProfileId, promotedProfileId);
        return ResponseEntity.ok(groupMembership);
    }

    @PatchMapping("/{groupId}/change-name/{adminProfileId}")
    @Operation(summary = "Change the name of a group")
    public ResponseEntity<Group> changeGroupName(@PathVariable @Parameter(description = "ID of the group") Long groupId, @PathVariable @Parameter(description = "ID of the admin profile") Long adminProfileId, @RequestBody @Parameter(description = "New name for the group") String newName) {
        Group group = groupService.changeGroupName(groupId, adminProfileId, newName);
        return ResponseEntity.ok(group);
    }

    @PutMapping("/{groupId}/promote-admin/{adminProfileId}/{promotedProfileId}")
    @Operation(summary = "Promote a member to admin")
    public ResponseEntity<GroupMembership> promoteToAdmin(@PathVariable @Parameter(description = "ID of the group") Long groupId, @PathVariable @Parameter(description = "ID of the admin profile") Long adminProfileId, @PathVariable @Parameter(description = "ID of the profile to be promoted to admin") Long promotedProfileId) {
        GroupMembership groupMembership = groupService.promoteToAdmin(groupId, adminProfileId, promotedProfileId);
        return ResponseEntity.ok(groupMembership);
    }

    @PutMapping("/{groupId}/demote-moderator/{adminProfileId}/{demotedProfileId}")
    @Operation(summary = "Demote moderator to member", description = "Demotes a moderator to a regular member of a group.")
    public ResponseEntity<GroupMembership> demoteModeratorToMember(
            @Parameter(description = "ID of the group") @PathVariable Long groupId,
            @Parameter(description = "ID of the profile of the admin") @PathVariable Long adminProfileId,
            @Parameter(description = "ID of the profile being demoted") @PathVariable Long demotedProfileId) {
        GroupMembership groupMembership = groupService.demoteModeratorToMember(groupId, adminProfileId, demotedProfileId);
        return ResponseEntity.ok(groupMembership);
    }

    @DeleteMapping("/{groupId}/leave/{leavingProfileId}")
    @Operation(summary = "Leave group", description = "Allows a profile to leave a group.")
    public ResponseEntity<Void> leaveGroup(
            @Parameter(description = "ID of the group") @PathVariable Long groupId,
            @Parameter(description = "ID of the profile leaving the group") @PathVariable Long leavingProfileId) {
        groupService.leaveGroup(groupId, leavingProfileId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/remove-member/{moderatorProfileId}/{removedProfileId}")
    @Operation(summary = "Remove member by moderator", description = "Removes a member from a group by a moderator.")
    public ResponseEntity<Void> removeMemberByModerator(
            @Parameter(description = "ID of the group") @PathVariable Long groupId,
            @Parameter(description = "ID of the profile of the moderator") @PathVariable Long moderatorProfileId,
            @Parameter(description = "ID of the profile being removed") @PathVariable Long removedProfileId) {
        groupService.removeMemberByModerator(groupId, moderatorProfileId, removedProfileId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/remove-member-admin/{adminProfileId}/{removedProfileId}")
    @Operation(summary = "Remove member by admin", description = "Removes a member from a group by an admin.")
    public ResponseEntity<Void> removeMemberByAdmin(
            @Parameter(description = "ID of the group") @PathVariable Long groupId,
            @Parameter(description = "ID of the profile of the admin") @PathVariable Long adminProfileId,
            @Parameter(description = "ID of the profile being removed") @PathVariable Long removedProfileId) {
        groupService.removeMemberByAdmin(groupId, adminProfileId, removedProfileId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/delete/{adminProfileId}")
    @Operation(summary = "Delete group", description = "Deletes a group.")
    public ResponseEntity<Void> deleteGroup(
            @Parameter(description = "ID of the group") @PathVariable Long groupId,
            @Parameter(description = "ID of the profile of the admin") @PathVariable Long adminProfileId) {
        groupService.deleteGroup(groupId, adminProfileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-name/{groupName}")
    @Operation(summary = "Get group by name", description = "Retrieves a group by its name.")
    public ResponseEntity<Group> getGroupByName(
            @Parameter(description = "Name of the group") @PathVariable String groupName) {
        Group group = groupService.getGroupByName(groupName);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{profileId}/groups")
    @Operation(summary = "Get groups by profile ID", description = "Retrieves all groups that a profile is a member of.")
    public ResponseEntity<List<Group>> getGroupsByProfileId(
            @Parameter(description = "The ID of the profile to get groups for.")
            @PathVariable Long profileId) {
        List<Group> groups = groupService.getGroupsByProfileId(profileId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/memberships/{profileId}")
    @Operation(summary = "Get group memberships by profile ID", tags = {"Group Memberships"})
    public ResponseEntity<Set<GroupMembership>> getGroupMembershipsByProfileId(
            @Parameter(description = "The ID of the profile to get group memberships for.")
            @PathVariable Long profileId) {
        Set<GroupMembership> groupMemberships = groupService.getGroupMembershipsByProfileId(profileId);
        return ResponseEntity.ok(groupMemberships);
    }

    @GetMapping("/{groupId}/{profileId}/membership")
    @Operation(summary = "Get group membership by group ID and profile ID", tags = {"Group Memberships"})
    public ResponseEntity<GroupMembership> getGroupMembershipByGroupIdAndProfileId(
            @Parameter(description = "The ID of the group to get membership for.")
            @PathVariable Long groupId,
            @Parameter(description = "The ID of the profile to get membership for.")
            @PathVariable Long profileId) {
        GroupMembership groupMembership = groupService.getGroupMembershipByGroupIdAndProfileId(groupId, profileId);
        return ResponseEntity.ok(groupMembership);
    }

    @GetMapping("/search/{partOfName}")
    @Operation(summary = "Search for groups by part of name", tags = {"Group Search"})
    public ResponseEntity<List<Group>> getGroupsByPartOfName(
            @Parameter(description = "The part of the name to search for in group names.")
            @PathVariable String partOfName) {
        List<Group> groups = groupService.getGroupsByPartOfName(partOfName);
        return ResponseEntity.ok(groups);
    }
}