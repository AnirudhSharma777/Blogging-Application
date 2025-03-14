package com.example.blogapplication.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponseDto {

    private String id;
    private String title;
    private String content;
    private String author;
    private String userId;
    private String createdAt;
}
