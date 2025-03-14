package com.example.blogapplication.ResponseDto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    
    private String errorCode;
    private String errorMessage;
}
