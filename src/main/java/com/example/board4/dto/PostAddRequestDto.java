package com.example.board4.dto;

import lombok.Getter;

@Getter
public class PostAddRequestDto {
    private String title;
    private String author;
    private String password;
    private String content;
}
