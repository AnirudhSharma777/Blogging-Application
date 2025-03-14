package com.example.blogapplication.Services;

import com.example.blogapplication.Dto.LoginDto;
import com.example.blogapplication.Dto.RegisterDto;
import com.example.blogapplication.Dto.VerifyUserDto;
import com.example.blogapplication.Entities.User;
import com.example.blogapplication.ResponseDto.LoginResponseDto;


public interface AuthService {
    
    User signUp(RegisterDto registerDto) throws Exception;
    LoginResponseDto login(LoginDto loginDto) throws Exception;
    void verifyUser(VerifyUserDto verifyUserDto);
    void resendVerficationCode(String email);
}
