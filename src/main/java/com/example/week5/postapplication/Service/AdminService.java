package com.example.week5.postapplication.Service;

import com.example.week5.postapplication.DTO.AuthorizationAssignmentRequest;
import com.example.week5.postapplication.DTO.UserDto;
import com.example.week5.postapplication.Entities.Enums.Permission;
import com.example.week5.postapplication.Entities.Enums.Role;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;


     public UserDto assignRolesAndPrivilegesToUser(Long userId, AuthorizationAssignmentRequest authorizationAssignmentRequest){

         Set<Role> roleSet = authorizationAssignmentRequest.getRoles();
         Set<Permission> privileges = authorizationAssignmentRequest.getPermissions();

         User user = userService.getUserById(userId);

         //assigning ROLES
        if(user.getRoles() == null)
            user.setRoles(roleSet);
        else
            user.getRoles().addAll(roleSet);

        //assigning PRIVILEGES
         if(user.getPermissions() == null)
             user.setPermissions(privileges);
         else
             user.getPermissions().addAll(privileges);

        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

}
