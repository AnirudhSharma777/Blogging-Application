package com.example.blogapplication.Exceptions;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(String msg) {
        super(msg);
    }

    public AccountAlreadyExistsException(){
        super();
    }
}
