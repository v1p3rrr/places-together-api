package com.vpr.placestogetherapi.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.vpr.placestogetherapi.model.entity.Account;
import com.vpr.placestogetherapi.model.entity.IdTokenRequest;
import com.vpr.placestogetherapi.service.AccountProfileService;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API")
public class AuthenticationController {

    private final AccountProfileService accountProfileService;

    @Value("${web.client.id}")
    private String webClientId;

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate using Google OAuth2")
    public ResponseEntity<Account> authenticate(
            @Parameter(description = "The id token issued by Google after authentication")
            @RequestBody IdTokenRequest idTokenRequest
    ) {
        // Verify the idToken with Google

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(webClientId))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenRequest.getIdToken());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = (String) payload.get("email");
                Account account = accountProfileService.createOrUpdateAccountWithOAuth2(email);
                return ResponseEntity.ok(account);
            } else {
                throw new IllegalArgumentException("Invalid id token");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalStateException("Failed to verify id token", e);
        }
    }
}