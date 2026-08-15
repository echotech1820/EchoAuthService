package com.echotech.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(error);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserDoesNotExist(UserDoesNotExistException ex) {

        ErrorResponse error = new ErrorResponse(
                "USER_DOES_NOT_EXIST",
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(error);
    }

}
