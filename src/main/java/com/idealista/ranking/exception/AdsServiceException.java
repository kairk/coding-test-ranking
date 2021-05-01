package com.idealista.ranking.exception;

import lombok.Getter;

@Getter
public class AdsServiceException extends Exception {
    private final String message;

    public AdsServiceException(String message) {
        super(message);

        this.message = message;
    }

    public AdsServiceException(String message, Throwable ex) {
        super(message, ex);

        this.message = message;
    }
}
