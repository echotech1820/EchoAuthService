package com.echotech.auth.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
