package com.example.blogapplication.Exceptions;


public class AccountNotVerifiedException extends RuntimeException {

    public AccountNotVerifiedException(String mgs){
        super(mgs);
    }

    public AccountNotVerifiedException(){
        super();
    }
}