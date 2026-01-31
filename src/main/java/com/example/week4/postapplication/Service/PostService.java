package com.example.week4.postapplication.Service;

import com.example.week4.postapplication.DTO.PostDTO;
import com.example.week4.postapplication.Entities.Post;

import java.util.List;


public interface PostService {

    PostDTO createPost(PostDTO post);

    List<PostDTO> getAllPosts();

    PostDTO findPostById(Long postId);
}
