package com.example.blogapplication.Exceptions;

public class VerificationCodeExpiredException extends RuntimeException{

    public VerificationCodeExpiredException(String msg){
        super(msg);
    }

    public VerificationCodeExpiredException(){
        super();
    }

}
