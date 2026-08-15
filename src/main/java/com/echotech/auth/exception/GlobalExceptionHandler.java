package com.echotech.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.echotech.auth.dto.ErrorResponse;
import com.echotech.auth.dto.ResponseDto;
import com.echotech.auth.util.UtilityClass;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private UtilityClass utilityClass = new UtilityClass();
	
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseDto> handleUserExists(UserAlreadyExistsException ex) {

    	ResponseDto error = new ResponseDto(
                utilityClass.failureCode,
                ex.getMessage(), 
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(error);
    }
    
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ResponseDto> handleWrongPwd(WrongPasswordException ex) {

    	ResponseDto error = new ResponseDto(
                utilityClass.failureCode,
                ex.getMessage(), 
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(error);
    }

}
