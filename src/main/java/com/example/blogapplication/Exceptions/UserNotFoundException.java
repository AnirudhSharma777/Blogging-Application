package com.example.blogapplication.Exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(){
        super();
    }
}
