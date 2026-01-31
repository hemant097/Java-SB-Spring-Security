package com.example.week4.postapplication.Repository;

import com.example.week4.postapplication.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
