package com.example.blogapplication.ResponseDto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String token;
    private long expiresIn;
}
