package com.vpr.placestogetherapi.repository;

import com.vpr.placestogetherapi.model.entity.Group;
import com.vpr.placestogetherapi.model.entity.GroupMembership;
import com.vpr.placestogetherapi.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Long> {
    Boolean existsByGroupAndProfile(Group group, Profile profile);
    Optional<GroupMembership> findByGroupAndProfile(Group group, Profile profile);
}
