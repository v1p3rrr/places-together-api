package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Friendship;
import com.vpr.placestogetherapi.model.entity.Profile;

public interface FriendshipService {
    Friendship requestFriendship(Profile profileRequest, Profile profileAccept);
    Friendship acceptFriendship(Profile profileRequest, Profile profileAccept);
    void removeFriendship(Profile profileRequest, Profile profileAccept);
}
