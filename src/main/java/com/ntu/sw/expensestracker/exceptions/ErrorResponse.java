package com.ntu.sw.expensestracker.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse (String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
