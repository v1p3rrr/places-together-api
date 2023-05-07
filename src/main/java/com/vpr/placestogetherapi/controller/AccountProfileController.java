package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.Account;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.service.AccountProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountProfileController {
    private final AccountProfileService accountProfileService;

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        Account account = accountProfileService.getAccount(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long profileId) {
        Profile profile = accountProfileService.getProfile(profileId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("account/username/{profileUsername}")
    public ResponseEntity<Account> getAccountByUsername(@PathVariable String profileUsername) {
        Account account = accountProfileService.getAccountByProfileUsername(profileUsername);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/profile/username/{profileUsername}")
    public ResponseEntity<Profile> getProfileByUsername(@PathVariable String profileUsername) {
        Profile profile = accountProfileService.getProfileByUsername(profileUsername);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile/username-search/{partOfUsername}")
    public ResponseEntity<List<Profile>> getProfileByUsernamePart(@PathVariable String partOfUsername) {
        List<Profile> profiles = accountProfileService.getProfilesByPartOfName(partOfUsername);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/email")
    public ResponseEntity<Account> getAccountByEmail(@RequestBody String email) {
        Account account = accountProfileService.getAccountByEmail(email);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/profile/email")
    public ResponseEntity<Profile> getProfileByEmail(@RequestBody String email) {
        Profile profile = accountProfileService.getProfileByAccountEmail(email);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/create-with-generated-profile")
    public ResponseEntity<Account> createAccountWithGeneratedProfile(@RequestBody Account account) {
        Account savedAccount = accountProfileService.createAccountWithGeneratedProfile(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PostMapping("/profile/create-with-account/{accountId}")
    public ResponseEntity<Profile> createProfileAndLinkToAccount(@RequestBody Profile profile, @PathVariable Long accountId) {
        Profile savedProfile = accountProfileService.createProfileAndLinkToAccount(profile, accountId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @PatchMapping("/change-email/{accountId}")
    public ResponseEntity<Account> changeEmail(@PathVariable Long accountId, @RequestBody String newEmail) {
        Account updatedAccount = accountProfileService.changeEmail(accountId, newEmail);
        return ResponseEntity.ok(updatedAccount);
    }

    @PatchMapping("/change-password/{accountId}")
    public ResponseEntity<Account> changePassword(@PathVariable Long accountId, @RequestBody String newPassword) {
        Account updatedAccount = accountProfileService.changePassword(accountId, newPassword);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("account/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountProfileService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/profile/{profileId}/change-username")
    public ResponseEntity<Profile> changeUsername(@PathVariable Long profileId, @RequestBody String newUsername) {
        Profile updatedProfile = accountProfileService.changeUsername(profileId, newUsername);
        return ResponseEntity.ok(updatedProfile);
    }

    @PatchMapping("/profile/{profileId}/change-status")
    public ResponseEntity<Profile> changeStatus(@PathVariable Long profileId, @RequestBody String newStatus) {
        Profile updatedProfile = accountProfileService.changeStatus(profileId, newStatus);
        return ResponseEntity.ok(updatedProfile);
    }

    @PatchMapping("/profile/{profileId}/change-profile-picture")
    public ResponseEntity<Profile> changeProfilePicture(@PathVariable Long profileId, @RequestBody String newProfilePictureLink) {
        Profile updatedProfile = accountProfileService.changeProfilePicture(profileId, newProfilePictureLink);
        return ResponseEntity.ok(updatedProfile);
    }
}