package com.example.week5.postapplication.Service;

import com.example.week5.postapplication.DTO.UserDto;
import com.example.week5.postapplication.Entities.Enums.Role;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;


     public UserDto assignRolesToUser(Long userId, Set<Role> roleSet){

        User user = userService.getUserById(userId);

        if(user.getRoles() == null)
            user.setRoles(roleSet);
        else
            user.getRoles().addAll(roleSet);

        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }
}
