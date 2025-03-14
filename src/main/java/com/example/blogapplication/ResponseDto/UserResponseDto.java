package com.example.blogapplication.ResponseDto;

import java.util.List;

import com.example.blogapplication.Entities.Blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private String id;
    private String username;
    private String email;
    private String password;
    private String verification_code;
    private List<Blog> blogs;
    private String token;

}
