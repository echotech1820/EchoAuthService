package com.echotech.auth.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.echotech.auth.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExists(UserAlreadyExistsException ex) {

        ErrorResponse error = new ErrorResponse(
                "USER_ALREADY_EXISTS",
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(error);
    }
    
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPwd(WrongPasswordException ex) {

        ErrorResponse error = new ErrorResponse(
                "PASS_WORD_WRONG",
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        ErrorResponse error = new ErrorResponse("BAD_REQUEST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("RESOURCE_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableBody(HttpMessageNotReadableException ex) {
        ErrorResponse error = new ErrorResponse("BAD_REQUEST", "THE GIVEN REQUEST IS INVALID");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccess(DataAccessException ex) {
        ErrorResponse error = new ErrorResponse("DATA_ERROR", "A DATA ERROR OCCURRED WHILE PROCESSING THE REQUEST");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        ErrorResponse error = new ErrorResponse("INTERNAL_ERROR", "AN UNEXPECTED ERROR OCCURRED");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
