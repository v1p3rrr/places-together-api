package com.vpr.placestogetherapi.service;

import com.vpr.placestogetherapi.model.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountProfileService accountProfileService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Account account = accountProfileService.getAccountByEmail(email);
            return User.withUsername(account.getEmail())
                    .password(account.getPassword())
                    .authorities(Collections.emptyList())
                    .build();
        } catch (IllegalStateException e) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}