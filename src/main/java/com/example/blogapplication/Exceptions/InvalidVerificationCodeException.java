package com.example.blogapplication.Exceptions;

public class InvalidVerificationCodeException extends RuntimeException{

    public InvalidVerificationCodeException(String msg){
        super(msg);
    }

    public InvalidVerificationCodeException(){
        super();
    }
}
