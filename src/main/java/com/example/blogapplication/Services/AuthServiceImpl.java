package com.example.blogapplication.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogapplication.Dto.LoginDto;
import com.example.blogapplication.Dto.RegisterDto;
import com.example.blogapplication.Dto.VerifyUserDto;
import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Exceptions.AccountAlreadyExistsException;
import com.example.blogapplication.Exceptions.AccountNotVerifiedException;
import com.example.blogapplication.Exceptions.InvalidVerificationCodeException;
import com.example.blogapplication.Exceptions.SignUpFailedException;
import com.example.blogapplication.Exceptions.UserNotFoundException;
import com.example.blogapplication.Exceptions.VerificationCodeExpiredException;
import com.example.blogapplication.Repositories.UserRepository;
import com.example.blogapplication.ResponseDto.LoginResponseDto;
import com.example.blogapplication.Utils.JwtService;
import com.example.blogapplication.Utils.ServiceUtil;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ServiceUtil serviceUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            ServiceUtil serviceUtil,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            EmailServiceImpl emailService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.serviceUtil = serviceUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public User signUp(RegisterDto input) throws Exception {

        try {
            boolean isEmailExist = userRepository.findByEmail(input.getEmail()).isPresent();
            if (isEmailExist) {
                throw new AccountAlreadyExistsException("Email already exist");
            }

            var user = User.builder()
                    .name(input.getName())
                    .email(input.getEmail())
                    .password(passwordEncoder.encode(input.getPassword()))
                    .verificationCode(serviceUtil.generateVerificationCode())
                    .verificationCodeExpiresAt(LocalDateTime.now().plusMinutes(5))
                    .verificationDate(null)
                    .enabled(false)
                    .build();

            serviceUtil.sendVerificationEmail(user);
            var savedUser = userRepository.save(user);
            return savedUser;
        } catch (SignUpFailedException e) {
            throw new SignUpFailedException("Failed to sign up: " + e.getMessage());
        }
    }

    @Override
    public LoginResponseDto login(LoginDto request) throws Exception {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
    
            if (!user.isEnabled()) {
                throw new AccountNotVerifiedException("Account not verified. Please verify your account.");
            }
    
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
    
            var jwtToken = jwtService.generateToken(user);
            var expired = jwtService.getExpirationTime();
    
            saveUserToken(user, jwtToken);
    
            return LoginResponseDto.builder()
            .token(jwtToken)
            .expiresIn(expired)
            .build();

                    
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User Not Fount");
        } 
    }

    @Override
    public void verifyUser(VerifyUserDto verifyUserDto) {
        Optional<User> optionalUser = userRepository.findByEmail(verifyUserDto.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new VerificationCodeExpiredException("Verification code has expired");
            }

            if (user.getVerificationCode().equals(verifyUserDto.getVerificationcode())) {
                user.setVerificationCode(null);
                user.setVerificationCodeExpiresAt(null);
                user.setVerificationDate(LocalDateTime.now());
                user.setEnabled(true);
                userRepository.save(user);
            } else {
                throw new InvalidVerificationCodeException("Invalid verification code");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void resendVerficationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new AccountAlreadyExistsException("Account is already verified");
            }
            user.setVerificationCode(serviceUtil.generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            serviceUtil.sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        user.setToken(jwtToken);
        userRepository.save(user);
    }

}
