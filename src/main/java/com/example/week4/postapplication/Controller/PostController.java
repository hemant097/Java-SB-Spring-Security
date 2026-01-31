package com.example.week4.postapplication.Controller;

import com.example.week4.postapplication.DTO.PostDTO;
import com.example.week4.postapplication.Service.PostService;
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
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createNewPost(@RequestBody PostDTO input){
        return new ResponseEntity<>(postService.createPost(input), HttpStatus.OK);
    }

    @GetMapping(path = "/{postid}")
    public ResponseEntity<PostDTO> findPostById(@PathVariable(name = "postid") Long postId){
        return new ResponseEntity<>(postService.findPostById(postId),HttpStatus.OK);
    }


}
