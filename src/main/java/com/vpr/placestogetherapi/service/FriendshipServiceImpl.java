package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.model.enums.FriendshipStatus;
import com.vpr.placestogetherapi.repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    @Override
    public Friendship requestFriendship(Profile profileRequest, Profile profileAccept) {
        if (profileRequest.getId().equals(profileAccept.getId())) {
            throw new IllegalArgumentException("A profile cannot have a friendship with itself.");
        }

        // Check if a friendship already exists
        Optional<Friendship> existingFriendship1 = friendshipRepository.findByProfileRequestAndProfileAccept(profileRequest, profileAccept);
        Optional<Friendship> existingFriendship2 = friendshipRepository.findByProfileRequestAndProfileAccept(profileRequest, profileAccept);

        if (existingFriendship1.isPresent() || existingFriendship2.isPresent()) {
            throw new IllegalStateException("A friendship already exists between these profiles.");
        }

        // Create a new friendship
        Friendship friendship = new Friendship();
        friendship.setProfileRequest(profileRequest);
        friendship.setProfileAccept(profileAccept);
        friendship.setStatus(FriendshipStatus.REQUESTED);

        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship acceptFriendship(Profile profileRequest, Profile profileAccept) {
        Optional<Friendship> existingFriendship = friendshipRepository.findByProfileRequestAndProfileAccept(profileRequest, profileAccept);
        if (existingFriendship.isEmpty()) {
            existingFriendship = friendshipRepository.findByProfileRequestAndProfileAccept(profileAccept, profileRequest);
            if (existingFriendship.isEmpty()) {
                throw new IllegalStateException("There wasn't such request between these profiles");
            }
        }
        Friendship updatedFriendship = existingFriendship.get();

        if (updatedFriendship.getStatus()!=FriendshipStatus.REQUESTED) {
            throw new IllegalStateException("Friendship between these profiles isn't requested");
        }

        updatedFriendship.setStatus(FriendshipStatus.FRIENDS);

        return friendshipRepository.save(updatedFriendship);
    }

    @Override
    public void removeFriendship(Profile profileRequest, Profile profileAccept) {
        Optional<Friendship> existingFriendship = friendshipRepository.findByProfileRequestAndProfileAccept(profileRequest, profileAccept);
        if (existingFriendship.isEmpty()) {
            existingFriendship = friendshipRepository.findByProfileRequestAndProfileAccept(profileAccept, profileRequest);
            if (existingFriendship.isEmpty()) {
                throw new IllegalStateException("There wasn't such friendship between these profiles");
            }
        }
        Friendship updatedFriendship = existingFriendship.get();

        friendshipRepository.delete(updatedFriendship);
    }
}