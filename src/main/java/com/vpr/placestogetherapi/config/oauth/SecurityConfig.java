package com.vpr.placestogetherapi.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   UserDetailsService userDetailsService,
                                                   OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService) throws Exception {

        http    //.authorizeRequests
                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll() //todo disable auth
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(oAuth2UserService)
                        )
                )
                .userDetailsService(userDetailsService)
                .csrf().disable();

        return http.build();
    }
}