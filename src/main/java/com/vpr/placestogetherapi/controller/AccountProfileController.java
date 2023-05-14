package com.vpr.placestogetherapi.controller;

import com.vpr.placestogetherapi.model.entity.Account;
import com.vpr.placestogetherapi.model.entity.Profile;
import com.vpr.placestogetherapi.service.AccountProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account Profile API", description = "APIs for managing account profiles")
public class AccountProfileController {
    private final AccountProfileService accountProfileService;

    @Operation(summary = "Get account by ID")
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(
            @Parameter(description = "Account ID", required = true)
            @PathVariable Long accountId) {
        Account account = accountProfileService.getAccount(accountId);
        return ResponseEntity.ok(account);
    }

    @Operation(summary = "Get profile by ID")
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<Profile> getProfileById(
            @Parameter(description = "Profile ID", required = true)
            @PathVariable Long profileId) {
        Profile profile = accountProfileService.getProfile(profileId);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Get account by profile username")
    @GetMapping("username/{profileUsername}")
    public ResponseEntity<Account> getAccountByUsername(
            @Parameter(description = "Profile username", required = true)
            @PathVariable String profileUsername) {
        Account account = accountProfileService.getAccountByProfileUsername(profileUsername);
        return ResponseEntity.ok(account);
    }

    @Operation(summary = "Get profile by profile username")
    @GetMapping("/profile/username/{profileUsername}")
    public ResponseEntity<Profile> getProfileByUsername(
            @Parameter(description = "Profile username", required = true)
            @PathVariable String profileUsername) {
        Profile profile = accountProfileService.getProfileByUsername(profileUsername);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Get profiles by part of username")
    @GetMapping("/profile/username-search/{partOfUsername}")
    public ResponseEntity<List<Profile>> getProfileByUsernamePart(
            @Parameter(description = "Part of the profile username", required = true)
            @PathVariable String partOfUsername) {
        List<Profile> profiles = accountProfileService.getProfilesByPartOfName(partOfUsername);
        return ResponseEntity.ok(profiles);
    }

    @Operation(summary = "Get account by email")
    @GetMapping("/email")
    public ResponseEntity<Account> getAccountByEmail(
            @Parameter(description = "Account email", required = true)
            @RequestBody String email) {
        Account account = accountProfileService.getAccountByEmail(email);
        return ResponseEntity.ok(account);
    }

    @Operation(summary = "Get profile by account email")
    @GetMapping("/profile/email")
    public ResponseEntity<Profile> getProfileByEmail(
            @Parameter(description = "Account email", required = true)
            @RequestBody String email) {
        Profile profile = accountProfileService.getProfileByAccountEmail(email);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Create account with generated profile")
    @PostMapping("/create-with-generated-profile")
    public ResponseEntity<Account> createAccountWithGeneratedProfile(
            @Parameter(description = "Account object", required = true)
            @RequestBody Account account) {
        Account savedAccount = accountProfileService.createAccountWithGeneratedProfile(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @Operation(summary = "Create account without profile")
    @PostMapping("/create")
    public ResponseEntity<Account> createAccountWithoutProfile(
            @Parameter(description = "Account object", required = true)
            @RequestBody Account account) {
        Account savedAccount = accountProfileService.createAccountWithoutProfile(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @Operation(summary = "Create profile and link to account")
    @PostMapping("/profile/create-with-account/{accountId}")
    public ResponseEntity<Profile> createProfileAndLinkToAccount(
            @Parameter(description = "Profile object", required = true)
            @RequestBody Profile profile,
            @Parameter(description = "Account ID", required = true)
            @PathVariable Long accountId) {
        Profile savedProfile = accountProfileService.createProfileAndLinkToAccount(profile, accountId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @Operation(summary = "Change account email")
    @PatchMapping("/change-email/{accountId}")
    public ResponseEntity<Account> changeEmail(
            @Parameter(description = "Account ID", required = true)
            @PathVariable Long accountId,
            @Parameter(description = "New email", required = true)
            @RequestBody String newEmail) {
        Account updatedAccount = accountProfileService.changeEmail(accountId, newEmail);
        return ResponseEntity.ok(updatedAccount);
    }

    @Operation(summary = "Change account password")
    @PatchMapping("/change-password/{accountId}")
    public ResponseEntity<Account> changePassword(
            @Parameter(description = "Account ID", required = true)
            @PathVariable Long accountId,
            @Parameter(description = "New password", required = true)
            @RequestBody String newPassword) {
        Account updatedAccount = accountProfileService.changePassword(accountId, newPassword);
        return ResponseEntity.ok(updatedAccount);
    }

    @Operation(summary = "Delete account")
    @DeleteMapping("account/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "Account ID", required = true)
            @PathVariable Long accountId) {
        accountProfileService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change profile username")
    @PatchMapping("/profile/{profileId}/change-username")
    public ResponseEntity<Profile> changeUsername(
            @Parameter(description = "Profile ID", required = true)
            @PathVariable Long profileId,
            @Parameter(description = "New username", required = true)
            @RequestBody String newUsername) {
        Profile updatedProfile = accountProfileService.changeUsername(profileId, newUsername);
        return ResponseEntity.ok(updatedProfile);
    }

    @Operation(summary = "Change profile status")
    @PatchMapping("/profile/{profileId}/change-status")
    public ResponseEntity<Profile> changeStatus(
            @Parameter(description = "Profile ID", required = true)
            @PathVariable Long profileId,
            @Parameter(description = "New status", required = true)
            @RequestBody String newStatus) {
        Profile updatedProfile = accountProfileService.changeStatus(profileId, newStatus);
        return ResponseEntity.ok(updatedProfile);
    }

    @Operation(summary = "Change profile picture")
    @PatchMapping("/profile/{profileId}/change-profile-picture")
    public ResponseEntity<Profile> changeProfilePicture(
            @Parameter(description = "Profile ID", required = true)
            @PathVariable Long profileId,
            @Parameter(description = "New profile picture link", required = true)
            @RequestBody String newProfilePictureLink) {
        Profile updatedProfile = accountProfileService.changeProfilePicture(profileId, newProfilePictureLink);
        return ResponseEntity.ok(updatedProfile);
    }

    @Operation(summary = "Update profile")
    @PutMapping("/profile/{profileId}/update-profile")
    public ResponseEntity<Profile> updateProfile(
            @Parameter(description = "Profile ID", required = true)
            @PathVariable Long profileId,
            @Parameter(description = "Updated profile object", required = true)
            @RequestBody Profile profile) {
        Profile updatedProfile = accountProfileService.updateProfile(profileId, profile);
        return ResponseEntity.ok(updatedProfile);
    }
}