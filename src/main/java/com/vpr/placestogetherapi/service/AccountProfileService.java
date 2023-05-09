package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Account;
import com.vpr.placestogetherapi.model.entity.Profile;

import java.util.List;

public interface AccountProfileService {

    Account createOrUpdateAccountWithOAuth2(String email);

    Account getAccount(Long accountId);

    Profile getProfile(Long profileId);

    List<Profile> getProfilesByPartOfName(String partOfName);

    Account getAccountByEmail(String email);

    Profile getProfileByAccountEmail(String email);

    Account getAccountByProfileUsername(String username);

    Profile getProfileByUsername(String username);

    Account createAccountWithGeneratedProfile(Account account);

    Account createAccountWithoutProfile(Account account);

    Profile createProfileAndLinkToAccount(Profile profile, Long accountId);

    Account changePassword(Long accountId, String newPassword);

    Account changeEmail(Long accountId, String newEmail);

    void deleteAccount(Long accountId);

    Profile changeUsername(Long profileId, String newUsername);

    Profile changeStatus(Long profileId, String newStatus);

    Profile changeProfilePicture(Long profileId, String newProfilePictureLink);

    Profile updateProfile(Long profileId, Profile profile);
}
