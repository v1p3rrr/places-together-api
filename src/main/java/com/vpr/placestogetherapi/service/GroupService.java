package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Group;
import com.vpr.placestogetherapi.model.entity.GroupMembership;

import java.util.List;
import java.util.Set;

public interface GroupService {
    Group getGroupById(Long groupId);

    Group getGroupByName(String groupName);

    List<Group> getGroupsByProfileId(Long profileId);

    Set<GroupMembership> getGroupMembershipsByGroupId(Long groupId);

    Set<GroupMembership> getGroupMembershipsByProfileId(Long groupId);

    GroupMembership getGroupMembershipByGroupIdAndProfileId(Long groupId, Long profileId);

    List<Group> getGroupsByPartOfName(String partOfName);

    Group createGroup(String groupName, Long adminProfileId);

    GroupMembership inviteProfileToGroup(Long groupId, Long invitedProfileId);

    GroupMembership promoteMemberToModerator(Long groupId, Long adminProfileId, Long promotedProfileId);

    GroupMembership promoteToAdmin(Long groupId, Long adminProfileId, Long promotedProfileId);

    GroupMembership demoteModeratorToMember(Long groupId, Long adminProfileId, Long demotedProfileId);

    void leaveGroup(Long groupId, Long leavingProfileId);

    void removeMemberByModerator(Long groupId, Long moderatorProfileId, Long removedProfileId);

    void removeMemberByAdmin(Long groupId, Long adminProfileId, Long removedProfileId);

    void deleteGroup(Long groupId, Long adminProfileId);
}
