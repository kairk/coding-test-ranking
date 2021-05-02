package com.idealista.ranking.exception.handler;

import com.idealista.ranking.exception.AdsNoContentException;
import com.idealista.ranking.exception.GenericAdsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@Component
public class AdsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AdsNoContentException.class)
    protected ResponseEntity<Object> handleAdsNoContent(AdsNoContentException ex, WebRequest request) {
        String bodyOfResponse = "Empty result";

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler(value = GenericAdsException.class)
    protected ResponseEntity<Object> handleGenericError(GenericAdsException ex, WebRequest request) {
        String bodyOfResponse = "An error occurred while processing the request";

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getStatus(), request);
    }
}
