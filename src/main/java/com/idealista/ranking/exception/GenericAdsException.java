package com.idealista.ranking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericAdsException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public GenericAdsException(String message, HttpStatus status) {
        super(message);

        this.message = message;
        this.status = status;
    }

    public GenericAdsException(String message, Throwable ex, HttpStatus status) {
        super(message, ex);

        this.message = message;
        this.status = status;
    }

    public GenericAdsException(String message) {
        super(message);

        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public GenericAdsException(String message, Throwable ex) {
        super(message, ex);

        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
