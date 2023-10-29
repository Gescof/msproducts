package com.example.products.application.rest.price.handler;

import com.example.products.application.rest.price.response.ErrorResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class PriceControllerAdvice {

    private final Clock clock;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidExceptionException(final MethodArgumentNotValidException ex) {
        log.warn(String.format("Handling MethodArgumentNotValidException: %s", ex.getMessage()));

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(objectError ->
                errors.add(objectError.getObjectName() + ": " + objectError.getDefaultMessage()));

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now(clock))
                .message(ex.getMessage())
                .errors(errors)
                .build();
    }
}
