package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
