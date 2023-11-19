package com.example.board4.controller;

import com.example.board4.controller.exception.AuthorizeException;
import com.example.board4.controller.exception.PostNotFoundException;
import com.example.board4.dto.PostAddRequestDto;
import com.example.board4.dto.PostResponseDto;
import com.example.board4.dto.PostUpdateRequestDto;
import com.example.board4.dto.exception.ErrorResponseDto;
import com.example.board4.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(
            @RequestBody PostAddRequestDto requestDto
    ) {
        PostResponseDto responseDto = postService.addPost(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable Long postId
    ) {
        PostResponseDto responseDto = postService.getPost(postId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> responseDto = postService.getPosts();
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto requestDto
    ) {
        PostResponseDto responseDto = postService.updatePost(postId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestHeader("password") String password
    ) {
        postService.deletePost(postId, password);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> postNotFoundExceptionHandler(PostNotFoundException ex) {
//        System.err.println(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity<ErrorResponseDto> postNotFoundExceptionHandler(AuthorizeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDto(
                        HttpStatus.UNAUTHORIZED.value(),
                        ex.getMessage()
                )
        );
    }
}
