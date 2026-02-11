package com.example.week5.postapplication.Service;

import com.example.week5.postapplication.DTO.PostDto;
import com.example.week5.postapplication.Entities.Post;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Exceptions.ResourceNotFoundException;
import com.example.week5.postapplication.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    private final ModelMapper modelMapper;
    private final PostRepository postRepo;

    @Override
    public PostDto createPost(PostDto inputPost) {

        Post post = modelMapper.map(inputPost, Post.class);

        System.out.println(post.getTitle());

        return modelMapper.map(postRepo.save(post), PostDto.class);


    }

    @Override
    public List<PostDto> getAllPosts() {
       List<Post> posts = postRepo.findAll();

      return posts
              .stream()
              .map(post->modelMapper.map(post, PostDto.class))
              .collect(Collectors.toList());
    }

    @Override
    public PostDto findPostById(Long postId) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("user with email {} is trying to access post with id  {}",user.getUsername(), postId);
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("post not found with id "+postId));

        return modelMapper.map(post,PostDto.class);
    }
}
