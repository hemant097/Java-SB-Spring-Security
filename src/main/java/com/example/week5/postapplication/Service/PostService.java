package com.example.week5.postapplication.Service;

import com.example.week5.postapplication.DTO.PostDto;

import java.util.List;


public interface PostService {

    PostDto createPost(PostDto post);

    List<PostDto> getAllPosts();

    PostDto findPostById(Long postId);

    void deleteAPost(Long postId);
}
