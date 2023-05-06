package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Account;
import com.vpr.placestogetherapi.model.entity.Profile;

public interface AccountProfileService {
    public Account createAccountWithGeneratedProfile(Account account);

    public Profile createProfileAndLinkToAccount(Profile profile, Long accountId);

    public Account changePassword(Long accountId, String newPassword);

    public void deleteAccount(Long accountId);

    public Profile changeUsername(Long profileId, String newUsername);
}
