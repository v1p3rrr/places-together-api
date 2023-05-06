package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Account;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.repository.AccountRepository;
import com.vpr.placestogetherapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountProfileServiceImpl implements AccountProfileService {

    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;

    @Override
    public Account createAccountWithGeneratedProfile(Account account) {
        // Save the account
        Account savedAccount = accountRepository.save(account);

        // Create the profile with the username based on the email before "@"
        Profile profile = new Profile();
        String username = account.getEmail().substring(0, account.getEmail().indexOf('@'));
        profile.setUsername(username);
        profile.setAccount(savedAccount);

        // Save the profile
        profileRepository.save(profile);

        // Update the saved account with the created profile
        savedAccount.setProfile(profile);

        return savedAccount;
    }

    @Override
    public Profile createProfileAndLinkToAccount(Profile profile, Long accountId) {
        // Find the account by accountId
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account with id " + accountId + " not found"));

        // Link the profile to the account and save it
        profile.setAccount(account);
        Profile savedProfile = profileRepository.save(profile);

        // Update the account with the created profile and save it
        account.setProfile(savedProfile);
        accountRepository.save(account);

        return savedProfile;
    }

    @Override
    public Account changePassword(Long accountId, String newPassword) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account with id " + accountId + " not found"));

        account.setPassword(newPassword);

        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account with id " + accountId + " not found"));

        // Delete the profile
        Profile profile = account.getProfile();
        if (profile != null) {
            profileRepository.delete(profile);
        }

        // Delete the account
        accountRepository.delete(account);
    }

    @Override
    public Profile changeUsername(Long profileId, String newUsername) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalStateException("Profile with id " + profileId + " not found"));

        profile.setUsername(newUsername);

        return profileRepository.save(profile);
    }

}
