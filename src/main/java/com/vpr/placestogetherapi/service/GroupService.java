package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Group;
import com.vpr.placestogetherapi.model.entity.GroupMembership;
import com.vpr.placestogetherapi.model.entity.Profile;

public interface GroupService {
    public Group createGroup(String groupName, Long adminProfileId);

    public GroupMembership inviteProfileToGroup(Long groupId, Long invitedProfileId);

    public GroupMembership promoteMemberToModerator(Long groupId, Long adminProfileId, Long promotedProfileId);

    public GroupMembership promoteToAdmin(Long groupId, Long adminProfileId, Long promotedProfileId);

    public GroupMembership demoteModeratorToMember(Long groupId, Long adminProfileId, Long demotedProfileId);

    public void leaveGroup(Long groupId, Long leavingProfileId);

    public void removeMemberByModerator(Long groupId, Long moderatorProfileId, Long removedProfileId);

    public void removeMemberByAdmin(Long groupId, Long adminProfileId, Long removedProfileId);

    public void deleteGroup(Long groupId, Long adminProfileId);
}
