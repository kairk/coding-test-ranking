package com.idealista.ranking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AdsNoContentException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public AdsNoContentException(String message, HttpStatus status) {
        super(message);

        this.message = message;
        this.status = status;
    }

    public AdsNoContentException(String message, Throwable ex, HttpStatus status) {
        super(message, ex);

        this.message = message;
        this.status = status;
    }

    public AdsNoContentException(String message) {
        super(message);

        this.message = message;
        this.status = HttpStatus.NO_CONTENT;
    }

    public AdsNoContentException(String message, Throwable ex) {
        super(message, ex);

        this.message = message;
        this.status = HttpStatus.NO_CONTENT;
    }
}
