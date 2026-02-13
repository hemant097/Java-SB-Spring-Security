package com.example.week5.postapplication.Utils;

import com.example.week5.postapplication.DTO.PostDto;
import com.example.week5.postapplication.Entities.Post;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

public boolean isOwnerOfPost(Long postId){
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    PostDto postDto = postService.findPostById(postId);

    return postDto.getAuthor().getId().equals(user.getId());




}

}

