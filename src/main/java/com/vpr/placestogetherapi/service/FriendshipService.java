package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;

public interface FriendshipService {
    Friendship addFriendship(Profile profileA, Profile profileB);
}
