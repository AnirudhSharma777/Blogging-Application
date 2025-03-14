package com.example.blogapplication.Exceptions;

public class SignUpFailedException extends RuntimeException {

    public SignUpFailedException(String msg) {
        super(msg);
    }

    public SignUpFailedException() {
        super();
    }

}
