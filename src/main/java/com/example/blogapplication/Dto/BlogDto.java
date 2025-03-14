package com.example.blogapplication.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private String author;
}
