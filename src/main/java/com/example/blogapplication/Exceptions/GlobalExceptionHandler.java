package com.example.blogapplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.blogapplication.ResponseDto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<Object> handleAccountNotVerifiedException(AccountNotVerifiedException ex) {
        return new ResponseEntity<>(new ErrorResponse("ACCOUNT_NOT_VERIFIED", ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<Object> handleAccountAlreadyExistsException(AccountAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse("ACCOUNT_ALREADY_EXISTS", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse("USER_NOT_FOUND", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SignUpFailedException.class)
    public ResponseEntity<Object> handleSignUpFailedException(SignUpFailedException ex) {
        return new ResponseEntity<>(new ErrorResponse("SIGNUP_FAILED", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<Object> handleInvalidVerificationCodeException(InvalidVerificationCodeException ex) {
        return new ResponseEntity<>(new ErrorResponse("INVALID_VERIFICATION_CODE", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VerificationCodeExpiredException.class)
    public ResponseEntity<Object> handleVerificationCodeExpiredException(VerificationCodeExpiredException ex) {
        return new ResponseEntity<>(new ErrorResponse("VERIFICATION_CODE_EXPIRED", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity<Object> handleBlogNotFoundException(BlogNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse("BLOG_NOT_FOUND", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    // Fallback for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
