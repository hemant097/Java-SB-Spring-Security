package com.example.week4.postapplication.Service;

import com.example.week4.postapplication.DTO.SignupDto;
import com.example.week4.postapplication.DTO.UserDto;
import com.example.week4.postapplication.Entities.User;
import com.example.week4.postapplication.Exceptions.ResourceNotFoundException;
import com.example.week4.postapplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("user with email "+username+" not found"));

        //as our user Entity is implementing UserDetails, thus we can return UserDetails object
    }

    public UserDto signUp(SignupDto signupDto){
        Optional<User> user = userRepository.findByEmail(signupDto.getEmail());

        if(user.isPresent())
            throw new BadCredentialsException("user with email already exists :"+signupDto.getEmail());

        User toCreate = modelMapper.map(signupDto,User.class);

        //password shouldn't be saved in plain text, must be hashed always
        toCreate.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        User savedUser = userRepository.save(toCreate);

        return modelMapper.map(savedUser,UserDto.class);

    }


    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow( () -> new UsernameNotFoundException("no user is present with id:"+userId));
    }
}
