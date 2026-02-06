package com.example.week5.postapplication.Repository;

import com.example.week5.postapplication.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;//

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u.email from User u where u.email=:email")
    Optional<String> findEmailByEmail(String email);

    Optional<User> findByEmail(String email);

}
