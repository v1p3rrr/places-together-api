package com.vpr.placestogetherapi.util.responce_message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String error;
    private String path;
    private String timestamp;

    public ErrorResponse(int status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = LocalDateTime.now().toString();
    }
}
