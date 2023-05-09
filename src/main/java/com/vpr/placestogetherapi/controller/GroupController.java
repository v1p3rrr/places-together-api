package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.Group;
import com.vpr.placestogetherapi.model.entity.GroupMembership;
import com.vpr.placestogetherapi.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroup(@PathVariable Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{groupId}/memberships")
    public ResponseEntity<Set<GroupMembership>> getGroupMemberships(@PathVariable Long groupId) {
        Set<GroupMembership> groupMemberships = groupService.getGroupMembershipsByGroupId(groupId);
        return ResponseEntity.ok(groupMemberships);
    }

    @PostMapping("/create/{adminProfileId}")
    public ResponseEntity<Group> createGroup(@RequestBody String groupName, @PathVariable Long adminProfileId) {
        Group group = groupService.createGroup(groupName, adminProfileId);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PostMapping("/{groupId}/invite/{invitedProfileId}")
    public ResponseEntity<GroupMembership> inviteProfileToGroup(@PathVariable Long groupId, @PathVariable Long invitedProfileId) {
        GroupMembership groupMembership = groupService.inviteProfileToGroup(groupId, invitedProfileId);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupMembership);
    }

    @PutMapping("/{groupId}/promote-moderator/{adminProfileId}/{promotedProfileId}")
    public ResponseEntity<GroupMembership> promoteMemberToModerator(@PathVariable Long groupId, @PathVariable Long adminProfileId, @PathVariable Long promotedProfileId) {
        GroupMembership groupMembership = groupService.promoteMemberToModerator(groupId, adminProfileId, promotedProfileId);
        return ResponseEntity.ok(groupMembership);
    }

    @PatchMapping("/{groupId}/change-name/{adminProfileId}")
    public ResponseEntity<Group> changeGroupName(@PathVariable Long groupId, @PathVariable Long adminProfileId, @RequestBody String newName) {
        Group group = groupService.changeGroupName(groupId, adminProfileId, newName);
        return ResponseEntity.ok(group);
    }

    @PutMapping("/{groupId}/promote-admin/{adminProfileId}/{promotedProfileId}")
    public ResponseEntity<GroupMembership> promoteToAdmin(@PathVariable Long groupId, @PathVariable Long adminProfileId, @PathVariable Long promotedProfileId) {
        GroupMembership groupMembership = groupService.promoteToAdmin(groupId, adminProfileId, promotedProfileId);
        return ResponseEntity.ok(groupMembership);
    }

    @PutMapping("/{groupId}/demote-moderator/{adminProfileId}/{demotedProfileId}")
    public ResponseEntity<GroupMembership> demoteModeratorToMember(@PathVariable Long groupId, @PathVariable Long adminProfileId, @PathVariable Long demotedProfileId) {
        GroupMembership groupMembership = groupService.demoteModeratorToMember(groupId, adminProfileId, demotedProfileId);
        return ResponseEntity.ok(groupMembership);
    }

    @DeleteMapping("/{groupId}/leave/{leavingProfileId}")
    public ResponseEntity<Void> leaveGroup(@PathVariable Long groupId, @PathVariable Long leavingProfileId) {
        groupService.leaveGroup(groupId, leavingProfileId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/remove-member/{moderatorProfileId}/{removedProfileId}")
    public ResponseEntity<Void> removeMemberByModerator(@PathVariable Long groupId, @PathVariable Long moderatorProfileId, @PathVariable Long removedProfileId) {
        groupService.removeMemberByModerator(groupId, moderatorProfileId, removedProfileId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/remove-member-admin/{adminProfileId}/{removedProfileId}")
    public ResponseEntity<Void> removeMemberByAdmin(@PathVariable Long groupId, @PathVariable Long adminProfileId, @PathVariable Long removedProfileId) {
        groupService.removeMemberByAdmin(groupId, adminProfileId, removedProfileId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/delete/{adminProfileId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId, @PathVariable Long adminProfileId) {
        groupService.deleteGroup(groupId, adminProfileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-name/{groupName}")
    public ResponseEntity<Group> getGroupByName(@PathVariable String groupName) {
        Group group = groupService.getGroupByName(groupName);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{profileId}/groups")
    public ResponseEntity<List<Group>> getGroupsByProfileId(@PathVariable Long profileId) {
        List<Group> groups = groupService.getGroupsByProfileId(profileId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/memberships/{profileId}")
    public ResponseEntity<Set<GroupMembership>> getGroupMembershipsByProfileId(@PathVariable Long profileId) {
        Set<GroupMembership> groupMemberships = groupService.getGroupMembershipsByProfileId(profileId);
        return ResponseEntity.ok(groupMemberships);
    }

    @GetMapping("/{groupId}/{profileId}/membership")
    public ResponseEntity<GroupMembership> getGroupMembershipByGroupIdAndProfileId(@PathVariable Long groupId, @PathVariable Long profileId) {
        GroupMembership groupMembership = groupService.getGroupMembershipByGroupIdAndProfileId(groupId, profileId);
        return ResponseEntity.ok(groupMembership);
    }

    @GetMapping("/search/{partOfName}")
    public ResponseEntity<List<Group>> getGroupsByPartOfName(@PathVariable String partOfName) {
        List<Group> groups = groupService.getGroupsByPartOfName(partOfName);
        return ResponseEntity.ok(groups);
    }
}