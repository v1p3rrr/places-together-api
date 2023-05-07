package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Boolean existsByName(String name);
    Optional<Group> findByName(String groupName);
    List<Group> findByNameContainingIgnoreCase(String partOfName);
    List<Group> findByMembershipsProfileId(Long profileId);

}
