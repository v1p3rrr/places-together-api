package com.vpr.placestogetherapi.model.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class IdTokenRequest {
    private String idToken;
}