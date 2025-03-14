package com.example.blogapplication.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Services.EmailServiceImpl;

import jakarta.mail.MessagingException;

@Service
public class ServiceUtil {

    private EmailServiceImpl emailService;

    public ServiceUtil(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    public String convertDateToString(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE: " + user.getVerificationCode();
        
        String htmlMessage = """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 40px; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                    <h2 style="color: #007bff; text-align: center;">Welcome to Blogging App!</h2>
                    <p style="font-size: 16px; line-height: 1.5; text-align: center;">
                        Please use the verification code below to verify your account:
                    </p>
                    <div style="background: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0; text-align: center;">
                        <p style="font-size: 24px; font-weight: bold; color: #333;">%s</p>
                    </div>
                    <p style="font-size: 14px; text-align: center; color: #666;">
                        If you didn’t request this, please ignore this email.
                    </p>
                </div>
            </body>
            </html>
        """.formatted(verificationCode);
        

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            // Handle email sending exception
            e.printStackTrace();
        }
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

   
}
