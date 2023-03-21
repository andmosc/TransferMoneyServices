package ru.andmosc.transferMoney.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(ErrorTransferCard.class)
    private ResponseEntity<ErrorResponse> errorVerificationCard(ErrorTransferCard e) {
        ErrorResponse response = ErrorResponse.getInstance();
        response.setMessage(e.getMessage(), e.getId());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorConfirmOperation.class)
    private ResponseEntity<ErrorResponse> errorConfirmOperation(ErrorConfirmOperation e) {
        ErrorResponse response = ErrorResponse.getInstance();
        response.setMessage(e.getMessage(), e.getId());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
