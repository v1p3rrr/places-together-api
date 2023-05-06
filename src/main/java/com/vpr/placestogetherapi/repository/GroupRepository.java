package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Boolean existsByName(String name);
}
