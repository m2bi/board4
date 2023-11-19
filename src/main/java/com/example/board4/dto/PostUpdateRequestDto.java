package com.example.board4.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    private String title;
    private String author;
    private String content;
    private String password;
}
