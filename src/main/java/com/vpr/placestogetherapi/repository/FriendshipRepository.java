package com.vpr.placestogetherapi.repository;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByProfileRequestAndProfileAccept(Profile profile1, Profile profile2);
    Set<Friendship> findByProfileRequestIdOrProfileAcceptId(Long profileRequestId, Long profileAcceptId);
    Set<Friendship> findByProfileRequestId(Long profileRequestId);
    Set<Friendship> findByProfileAcceptId(Long profileAcceptId);
}
