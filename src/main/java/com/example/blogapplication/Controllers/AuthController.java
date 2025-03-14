package com.example.blogapplication.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapplication.Dto.EmailRequest;
import com.example.blogapplication.Dto.LoginDto;
import com.example.blogapplication.Dto.RegisterDto;
import com.example.blogapplication.Dto.VerifyUserDto;
import com.example.blogapplication.Entities.User;
import com.example.blogapplication.ResponseDto.UserResponseDto;
import com.example.blogapplication.Services.AuthServiceImpl;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody RegisterDto user) throws Exception {
        User userEntity = authService.signUp(user);
        UserResponseDto response = UserResponseDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .verification_code(userEntity.getVerificationCode())
                .blogs(userEntity.getBlogs())
                .token(userEntity.getToken())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto user) throws Exception {
        return new ResponseEntity<>(authService.login(user), HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@Valid @RequestBody VerifyUserDto verifyUserDto) {
        try {

            authService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationToken(@Valid @RequestBody EmailRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be null or empty");
        }

        try {
            authService.resendVerficationCode(request.getEmail());
            return ResponseEntity.ok("Verification code resent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
