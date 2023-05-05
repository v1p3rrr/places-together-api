package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public Friendship addFriendship(Profile profileA, Profile profileB) {
        if (profileA.getId().equals(profileB.getId())) {
            throw new IllegalArgumentException("A profile cannot have a friendship with itself.");
        }

        // Ensure that profile1 always has a lower ID than profile2
        Profile profile1 = (profileA.getId() < profileB.getId()) ? profileA : profileB;
        Profile profile2 = (profileA.getId() < profileB.getId()) ? profileB : profileA;

        // Check if a friendship already exists
        Optional<Friendship> existingFriendship = friendshipRepository.findByProfile1AndProfile2(profile1, profile2);

        if (existingFriendship.isPresent()) {
            throw new IllegalStateException("A friendship already exists between these profiles.");
        }

        // Create a new friendship
        Friendship friendship = new Friendship();
        friendship.setProfile1(profile1);
        friendship.setProfile2(profile2);
        friendship.setStatus("Pending");

        return friendshipRepository.save(friendship);
    }
}