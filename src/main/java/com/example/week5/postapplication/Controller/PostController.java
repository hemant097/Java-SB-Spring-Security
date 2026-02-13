package com.example.week5.postapplication.Controller;

import com.example.week5.postapplication.DTO.PostDto;
import com.example.week5.postapplication.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping( path = "/new")
    @PreAuthorize("hasAnyRole('CREATOR','ADMIN')")
    public ResponseEntity<PostDto> createNewPost(@Valid @RequestBody PostDto input){
        return new ResponseEntity<>(postService.createPost(input), HttpStatus.OK);
    }

    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    @GetMapping(path = "/{postid}")
    public ResponseEntity<PostDto> findPostById(@PathVariable(name = "postid") Long postId){
        return new ResponseEntity<>(postService.findPostById(postId),HttpStatus.OK);
    }

    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId) OR hasAuthority('POST_UPDATE')")
    @PutMapping(path = "{postid}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable(name = "postid") Long postId,
                                                  @RequestBody PostDto postToUpdate){
        return new ResponseEntity<>(postService.updatePostById(postId,postToUpdate),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{postid}")
    @PreAuthorize("hasAnyAuthority('POST_DELETE')")
    public ResponseEntity<String> deletePost(@PathVariable(name = "postid") Long postId){
        postService.deleteAPost(postId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }


}
