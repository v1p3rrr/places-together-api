package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Group;
import com.vpr.placestogetherapi.model.entity.GroupMembership;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.model.enums.GroupMemberRole;
import com.vpr.placestogetherapi.repository.GroupMembershipRepository;
import com.vpr.placestogetherapi.repository.GroupRepository;
import com.vpr.placestogetherapi.repository.ProfileRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ProfileRepository profileRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    //private final EntityManager entityManager;

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
    }

    @Override
    public Group getGroupByName(String groupName) {
        return groupRepository.findByName(groupName)
                .orElseThrow(() -> new NoSuchElementException("Group not found with name: " + groupName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> getGroupsByProfileId(Long profileId) {
        return groupRepository.findByMembershipsProfileId(profileId);
    }

    @Override
    public List<Group> getGroupsByPartOfName(String partOfName) {
        return groupRepository.findByNameContainingIgnoreCase(partOfName);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<GroupMembership> getGroupMembershipsByGroupId(Long groupId) {
        return groupMembershipRepository.findByGroupId(groupId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<GroupMembership> getGroupMembershipsByProfileId(Long profileId) {
        return groupMembershipRepository.findByProfileId(profileId);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupMembership getGroupMembershipByGroupIdAndProfileId(Long groupId, Long profileId) {
        return groupMembershipRepository.findByGroupIdAndProfileId(groupId, profileId)
                .orElseThrow(() -> new NoSuchElementException("Profile with id " + profileId + " not found in group with id " + groupId));
    }

    @Override
    @Transactional
    public Group createGroup(String groupName, Long adminProfileId) {
        Profile adminProfile = profileRepository.findById(adminProfileId).orElseThrow(() -> new NoSuchElementException("Creator's profile not found"));

        if (groupRepository.existsByName(groupName)) {
            throw new IllegalStateException("Group name already exists");
        }
        Group group = new Group();
        group.setName(groupName);

        Group savedGroup = groupRepository.save(group);

        GroupMembership groupMembership = new GroupMembership();
        groupMembership.setGroup(savedGroup);
        groupMembership.setProfile(adminProfile);
        groupMembership.setRole(GroupMemberRole.ADMIN);

        groupMembershipRepository.save(groupMembership);

        //entityManager.refresh(adminProfile);

        return savedGroup;
    }

    @Override
    @Transactional
    public GroupMembership inviteProfileToGroup(Long groupId, Long invitedProfileId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        Profile invitedProfile = profileRepository.findById(invitedProfileId).orElseThrow(() -> new NoSuchElementException("Inviting profile not found"));

        if (groupMembershipRepository.existsByGroupAndProfile(group, invitedProfile)) {
            throw new IllegalStateException("User is already a member of the group");
        }

        GroupMembership groupMembership = new GroupMembership();
        groupMembership.setGroup(group);
        groupMembership.setProfile(invitedProfile);
        groupMembership.setRole(GroupMemberRole.MEMBER);

        return groupMembershipRepository.save(groupMembership);
    }

    @Override
    @Transactional
    public GroupMembership promoteMemberToModerator(Long groupId, Long adminProfileId, Long promotedProfileId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        Profile adminProfile = profileRepository.findById(adminProfileId).orElseThrow(() -> new NoSuchElementException("Admin's profile not found"));
        Profile promotedProfile = profileRepository.findById(promotedProfileId).orElseThrow(() -> new NoSuchElementException("Member's profile not found"));

        GroupMembership adminMembership = groupMembershipRepository.findByGroupAndProfile(group, adminProfile).orElseThrow(() -> new NoSuchElementException("Admin not found in the group"));
        if (adminMembership.getRole() != GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Only admins can promote members to moderators");
        }

        GroupMembership promotedMembership = groupMembershipRepository.findByGroupAndProfile(group, promotedProfile).orElseThrow(() -> new NoSuchElementException("Member not found in the group"));
        if (promotedMembership.getRole() == GroupMemberRole.MODERATOR || promotedMembership.getRole() == GroupMemberRole.ADMIN) {
            throw new IllegalStateException("User is already a moderator or admin");
        }

        promotedMembership.setRole(GroupMemberRole.MODERATOR);

        return groupMembershipRepository.save(promotedMembership);
    }

    @Override
    @Transactional
    public GroupMembership promoteToAdmin(Long groupId, Long adminProfileId, Long promotedProfileId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        Profile adminProfile = profileRepository.findById(adminProfileId).orElseThrow(() -> new NoSuchElementException("Admin's profile not found"));
        Profile promotedProfile = profileRepository.findById(promotedProfileId).orElseThrow(() -> new NoSuchElementException("Member's profile not found"));

        GroupMembership adminMembership = groupMembershipRepository.findByGroupAndProfile(group, adminProfile).orElseThrow(() -> new NoSuchElementException("Admin not found in the group"));
        if (adminMembership.getRole() != GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Only admins can promote members to admins");
        }

        GroupMembership promotedMembership = groupMembershipRepository.findByGroupAndProfile(group, promotedProfile).orElseThrow(() -> new NoSuchElementException("Member not found in the group"));
        if (promotedMembership.getRole() == GroupMemberRole.ADMIN) {
            throw new IllegalStateException("User is already admin");
        }

        promotedMembership.setRole(GroupMemberRole.ADMIN);

        return groupMembershipRepository.save(promotedMembership);
    }

    @Override
    @Transactional
    public GroupMembership demoteModeratorToMember(Long groupId, Long adminProfileId, Long demotedProfileId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        Profile adminProfile = profileRepository.findById(adminProfileId).orElseThrow(() -> new NoSuchElementException("Admin's profile not found"));
        Profile demotedProfile = profileRepository.findById(demotedProfileId).orElseThrow(() -> new NoSuchElementException("Moderator's profile not found"));

        GroupMembership adminMembership = groupMembershipRepository.findByGroupAndProfile(group, adminProfile).orElseThrow(() -> new NoSuchElementException("Admin not found in the group"));
        if (adminMembership.getRole() != GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Only admins can demote moderators to members");
        }

        GroupMembership demotedMembership = groupMembershipRepository.findByGroupAndProfile(group, demotedProfile).orElseThrow(() -> new NoSuchElementException("Member not found in the group"));
        if (demotedMembership.getRole() == GroupMemberRole.MEMBER) {
            throw new IllegalStateException("User is already a regular member");
        }
        if (demotedMembership.getRole() == GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Can't demote admin");
        }

        demotedMembership.setRole(GroupMemberRole.MEMBER);

        return groupMembershipRepository.save(demotedMembership);
    }

    @Override
    @Transactional
    public void leaveGroup(Long groupId, Long leavingProfileId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        Profile leavingProfile = profileRepository.findById(leavingProfileId).orElseThrow(() -> new NoSuchElementException("Leaving profile not found"));

        GroupMembership leavingMembership = groupMembershipRepository.findByGroupAndProfile(group, leavingProfile).orElseThrow(() -> new NoSuchElementException("Member not found in the group"));
        if (leavingMembership.getRole() == GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Admins cannot leave the group without transferring the admin role to another member or deleting group");
        }

        groupMembershipRepository.delete(leavingMembership);
    }

    @Override
    @Transactional
    public void removeMemberByModerator(Long groupId, Long moderatorProfileId, Long removedProfileId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        Profile moderatorProfile = profileRepository.findById(moderatorProfileId).orElseThrow(() -> new NoSuchElementException("Moderator's profile not found"));
        Profile removedProfile = profileRepository.findById(removedProfileId).orElseThrow(() -> new NoSuchElementException("Member's profile not found"));

        GroupMembership moderatorMembership = groupMembershipRepository.findByGroupAndProfile(group, moderatorProfile).orElseThrow(() -> new NoSuchElementException("Moderator not found in the group"));
        if (moderatorMembership.getRole() != GroupMemberRole.MODERATOR && moderatorMembership.getRole() != GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Only moderators and admins can remove members");
        }

        GroupMembership removingMembership = groupMembershipRepository.findByGroupAndProfile(group, removedProfile).orElseThrow(() -> new NoSuchElementException("Member not found in the group"));
        if (moderatorMembership.getRole() == GroupMemberRole.MODERATOR && removingMembership.getRole() != GroupMemberRole.MEMBER) {
            throw new IllegalStateException("Moderators can only remove members");
        }
        // extra checking for admin role if something
        if (moderatorMembership.getRole() != GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Only admin can remove moderators or other admins");
        }

        groupMembershipRepository.delete(removingMembership);
    }

    @Override
    @Transactional
    public void removeMemberByAdmin(Long groupId, Long adminProfileId, Long removedProfileId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        Profile adminProfile = profileRepository.findById(adminProfileId).orElseThrow(() -> new NoSuchElementException("Admin's profile not found"));
        Profile removedProfile = profileRepository.findById(removedProfileId).orElseThrow(() -> new NoSuchElementException("Member's profile not found"));

        GroupMembership adminMembership = groupMembershipRepository.findByGroupAndProfile(group, adminProfile).orElseThrow(() -> new NoSuchElementException("Admin not found in the group"));
        if (adminMembership.getRole() != GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Only admins can remove members and moderators");
        }

        GroupMembership removingMembership = groupMembershipRepository.findByGroupAndProfile(group, removedProfile).orElseThrow(() -> new NoSuchElementException("Member or moderator not found in the group"));

        groupMembershipRepository.delete(removingMembership);
    }

    @Override
    @Transactional
    public void deleteGroup(Long groupId, Long adminProfileId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NoSuchElementException("Group with id " + groupId + " not found"));
        Profile adminProfile = profileRepository.findById(adminProfileId).orElseThrow(() -> new NoSuchElementException("Profile not found"));

        GroupMembership adminMembership = groupMembershipRepository.findByGroupAndProfile(group, adminProfile).orElseThrow(() -> new NoSuchElementException("Admin not found in the group"));
        if (adminMembership.getRole() != GroupMemberRole.ADMIN) {
            throw new IllegalStateException("Only admins can delete the group");
        }

        groupMembershipRepository.deleteAll(group.getMemberships());

        groupRepository.delete(group);
    }
}
