package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Account;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.repository.AccountRepository;
import com.vpr.placestogetherapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountProfileServiceImpl implements AccountProfileService {

    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;

    @Override
    @Transactional
    public Account createOrUpdateAccountWithOAuth2(String email) {
        Optional<Account> accountOptional = accountRepository.findAccountByEmail(email);
        Account account;

        if (accountOptional.isPresent()) {
            account = accountOptional.get();
        } else {
            // Create a new account and profile if they do not exist
            account = new Account();
            account.setEmail(email);
            account.setPassword(""); // Set an empty password as it won't be used
            account = createAccountWithGeneratedProfile(account);
        }

        return account;
    }

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("Account with id " + accountId + " not found"));
    }

    @Override
    public Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> new IllegalStateException("Profile with id " + profileId + " not found"));
    }

    @Override
    public List<Profile> getProfilesByPartOfName(String partOfName) {
        return profileRepository.findByUsernameContainingIgnoreCase(partOfName);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email).orElseThrow(() -> new IllegalStateException("Account with email " + email + " not found"));
    }

    @Override
    public Profile getProfileByAccountEmail(String email) {
        return profileRepository.findProfileByAccountEmail(email).orElseThrow(() -> new IllegalStateException("Profile with linked email " + email + " not found"));
    }

    @Override
    public Account getAccountByProfileUsername(String username) {
        return accountRepository.findAccountByProfileUsername(username).orElseThrow(() -> new IllegalStateException("Account with linked username " + username + " not found"));
    }

    @Override
    public Profile getProfileByUsername(String username) {
        return profileRepository.findProfileByUsername(username).orElseThrow(() -> new IllegalStateException("Profile with username " + username + " not found"));
    }

    @Override
    @Transactional
    public Account createAccountWithGeneratedProfile(Account account) {
        // Save the account

        if (!account.getPassword().matches("^[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\{\\}\\[\\]\\\\\\|:;\"'<>,\\.\\?\\/]*$")) {
            throw new IllegalArgumentException("password contains forbidden symbols");
        }

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
    @Transactional
    public Account createAccountWithoutProfile(Account account) {
        // Save the account

        if (!account.getPassword().matches("^[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\{\\}\\[\\]\\\\\\|:;\"'<>,\\.\\?\\/]*$")) {
            throw new IllegalArgumentException("password contains forbidden symbols");
        }

        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Profile createProfileAndLinkToAccount(Profile profile, Long accountId) {
        // Find the account by accountId
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account with id " + accountId + " not found"));

        if (profileRepository.existsByAccountId(accountId)){
            throw new IllegalStateException("Profile for this account already exists");
        }

        if (!profile.getUsername().matches("^[a-zA-Z0-9!@#$%^&*()-_=+{}\\[\\]|;:'\",.<>/?\\s]+$")) {
            throw new IllegalArgumentException("Username contains forbidden symbols");
        }

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

        if (!newPassword.matches("^[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\{\\}\\[\\]\\\\\\|:;\"'<>,\\.\\?\\/]*$")) {
            throw new IllegalArgumentException("password contains forbidden symbols");
        }

        account.setPassword(newPassword);

        return accountRepository.saveAndFlush(account);
    }

    @Override
    public Account changeEmail(Long accountId, String newEmail) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account with id " + accountId + " not found"));

        account.setEmail(newEmail);

        return accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
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

    @Override
    public Profile changeStatus(Long profileId, String newStatus) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalStateException("Profile with id " + profileId + " not found"));

        profile.setStatus(newStatus);

        return profileRepository.save(profile);
    }

    @Override
    public Profile changeProfilePicture(Long profileId, String newProfilePictureLink) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalStateException("Profile with id " + profileId + " not found"));

        profile.setProfilePictureLink(newProfilePictureLink);

        return profileRepository.save(profile);
    }

    @Override
    public Profile updateProfile(Long profileId, Profile profile) {
        Profile extractedProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalStateException("Profile with id " + profileId + " not found"));
        extractedProfile.setUsername(profile.getUsername());
        extractedProfile.setStatus(profile.getStatus());
        extractedProfile.setProfilePictureLink(profile.getProfilePictureLink());

        return profileRepository.save(extractedProfile);
    }

}
