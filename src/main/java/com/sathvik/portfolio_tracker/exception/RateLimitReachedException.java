package com.sathvik.portfolio_tracker.exception;

public class RateLimitReachedException extends RuntimeException {
    public RateLimitReachedException(String message) {
        super(message);
    }
}
