package com.vpr.placestogetherapi.repository;

import com.vpr.placestogetherapi.model.entity.Group;
import com.vpr.placestogetherapi.model.entity.GroupMembership;
import com.vpr.placestogetherapi.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Long> {
    Boolean existsByGroupAndProfile(Group group, Profile profile);
    Optional<GroupMembership> findByGroupAndProfile(Group group, Profile profile);
    Set<GroupMembership> findByGroupId(Long groupId);
    Set<GroupMembership> findByProfileId(Long profileId);
    Optional<GroupMembership> findByGroupIdAndProfileId(Long groupId, Long profileId);
}
