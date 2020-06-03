package com.fvsen.apigateway.exception;

/**
 *
 * @author Fvsen
 * @date 2020/4/26/026 22:09
 */
public class RateLimitException extends RuntimeException {
    private Integer code;
    private String message;

    public RateLimitException(String message) {
        this.message = message;
    }
}
