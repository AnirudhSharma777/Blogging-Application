package com.example.blogapplication.Exceptions;

public class BlogNotFoundException extends RuntimeException{

    public BlogNotFoundException(String msg){
        super(msg);
    }

    public BlogNotFoundException(){
        super();
    }

}
