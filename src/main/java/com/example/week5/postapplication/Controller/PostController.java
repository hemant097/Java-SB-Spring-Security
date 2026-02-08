package com.example.week5.postapplication.Controller;

import com.example.week5.postapplication.DTO.PostDto;
import com.example.week5.postapplication.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> createNewPost(@Valid @RequestBody PostDto input){
        return new ResponseEntity<>(postService.createPost(input), HttpStatus.OK);
    }

    @GetMapping(path = "/{postid}")
    public ResponseEntity<PostDto> findPostById(@PathVariable(name = "postid") Long postId){
        return new ResponseEntity<>(postService.findPostById(postId),HttpStatus.OK);
    }


}
