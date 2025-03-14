package com.example.blogapplication.Exceptions;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String msg){
        super(msg);
    }

    public AccessDeniedException(){
        super();
    }
}
