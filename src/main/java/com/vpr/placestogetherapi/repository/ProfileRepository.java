package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.model.enums.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileByAccountEmail(String accountEmail);
    Optional<Profile> findProfileByUsername(String username);
    Boolean existsByAccountId(Long accountId);
    List<Profile> findByUsernameContainingIgnoreCase(String partOfName);

    /**
     * @param profileRequestId The ID of the profile, which should be the same as profileAcceptId
     * @param profileAcceptId The ID of the profile, which should be the same as profileRequestId.
     * @return find all friends or requests of some profile
     */
    Set<Profile> findByFriendshipRequestProfileRequestIdOrFriendshipAcceptProfileAcceptId(Long profileRequestId, Long profileAcceptId);

    /**
     * @param profileRequestId The ID of the profile whose friends are being searched, which should be the same as profileAcceptId
     * @param friendshipStatus1 Status of friendship, should be same for friendshipStatus2
     * @param profileAcceptId The ID of the profile whose friends are being searched, which should be the same as profileRequestId.
     * @param friendshipStatus2 Status of friendship, should be same for friendshipStatus1
     * @return find all friends or requests of some profile considering specified type
     */
    Set<Profile> findByFriendshipRequestProfileRequestIdAndFriendshipRequestStatusOrFriendshipAcceptProfileAcceptIdAndFriendshipAcceptStatus(Long profileRequestId, FriendshipStatus friendshipStatus1, Long profileAcceptId, FriendshipStatus friendshipStatus2);

    /**
     * @param profileId The ID of the profile whose friends are being searched
     * @return find all requested friends of some profile
     */
    Set<Profile> findByFriendshipRequestProfileRequestId(Long profileId);

    /**
     * @param profileId The ID of the profile whose friends are being searched
     * @param friendshipStatus Status of friendship
     * @return find all requested friends of some profile considering specified type (usually for sent requests ({@link FriendshipStatus} REQUESTED)
     */
    Set<Profile> findByFriendshipRequestProfileRequestIdAndFriendshipRequestStatus(Long profileId, FriendshipStatus friendshipStatus);

    /**
     * @param profileId The ID of the profile whose friends are being searched
     * @return find all accepting friends of some profile
     */
    Set<Profile> findByFriendshipAcceptProfileAcceptId(Long profileId);

    /**
     * @param profileId The ID of the profile whose friends are being searched
     * @param friendshipStatus Status of friendship
     * @return find all accepting friends of some profile considering specified type (usually for incoming requests ({@link FriendshipStatus} REQUESTED)
     */
    Set<Profile> findByFriendshipAcceptProfileAcceptIdAndFriendshipAcceptStatus(Long profileId, FriendshipStatus friendshipStatus);

    /**
     * @param profileId1 The ID of the profile whose friend is being searched, should be the same as profileId2
     * @param profileRequestId The ID of the profile that is being checked if its in friend list, should be the same as profileAcceptId
     * @param profileId2 The ID of the profile whose friend is being searched, should be the same as profileId1
     * @param profileAcceptId The ID of the profile that is being checked if its in friend list, should be the same as profileRequestId
     * @return true if two specified profiles are friends or have incoming request, false otherwise
     */
    Boolean existsByIdAndFriendshipRequestProfileRequestIdOrIdAndFriendshipAcceptProfileAcceptId(Long profileId1, Long profileRequestId, Long profileId2, Long profileAcceptId);

    /**
     * @param profileId1 The ID of the profile whose friend is being searched, should be the same as profileId2
     * @param friendshipStatus1 Status of friendship, should be same for friendshipStatus2
     * @param profileRequestId The ID of the profile that is being checked if its in friend list, should be the same as profileAcceptId
     * @param profileId2 The ID of the profile whose friend is being searched, should be the same as profileId1
     * @param friendshipStatus2 Status of friendship, should be same for friendshipStatus1
     * @param profileAcceptId The ID of the profile that is being checked if its in friend list, should be the same as profileRequestId
     * @return true if two specified profiles are friends with specified status, false otherwise
     */
    Boolean existsByIdAndFriendshipRequestStatusAndFriendshipRequestProfileRequestIdOrIdAndFriendshipAcceptStatusAndFriendshipAcceptProfileAcceptId(Long profileId1, FriendshipStatus friendshipStatus1, Long profileRequestId, Long profileId2, FriendshipStatus friendshipStatus2, Long profileAcceptId);

    /**
     * @param profileId The ID of the profile whose friend is being searched
     * @param friendshipStatus Status of friendship
     * @param profileRequestId The ID of the profile that is being checked if its in friend list
     * @return true if profile got friendship request from profileRequest and this friendship has specified status
     */
    Boolean existsByIdAndFriendshipRequestStatusAndFriendshipRequestProfileRequestId(Long profileId, FriendshipStatus friendshipStatus, Long profileRequestId);

    /**
     * @param profileId The ID of the profile whose friend is being searched
     * @param friendshipStatus Status of friendship
     * @param profileAcceptId The ID of the profile that is being checked if its in friend list
     * @return true if profile sent friendship request to profileAccept and this friendship has specified status
     */
    Boolean existsByIdAndFriendshipAcceptStatusAndFriendshipAcceptProfileAcceptId(Long profileId, FriendshipStatus friendshipStatus, Long profileAcceptId);

}
