package com.vpr.placestogetherapi.repository;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.model.enums.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByProfileRequestAndProfileAccept(Profile profileRequest, Profile profileAccept);
    Optional<Friendship> findByProfileRequestIdAndProfileAcceptId(Long profileRequestId, Long profileAcceptId);
    @Query("SELECT f.status FROM Friendship f WHERE f.profileRequest.id = :profileRequestId AND f.profileAccept.id = :profileAcceptId")
    FriendshipStatus findStatusByProfileRequestIdAndProfileAcceptId(Long profileRequestId, Long profileAcceptId);
    Set<Friendship> findByProfileRequestId(Long profileRequestId);
    Set<Friendship> findByProfileAcceptId(Long profileAcceptId);

}
