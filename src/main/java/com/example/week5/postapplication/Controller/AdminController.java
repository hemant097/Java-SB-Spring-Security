package com.example.week5.postapplication.Controller;

import com.example.week5.postapplication.DTO.AuthorizationAssignmentRequest;
import com.example.week5.postapplication.DTO.UserDto;
import com.example.week5.postapplication.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/{userid}")
    ResponseEntity<UserDto> assignRoles(@PathVariable(name = "userid") Long userId,
                                        @RequestBody AuthorizationAssignmentRequest authorizationAssignmentRequest){

        UserDto userDto = adminService.assignRolesAndPrivilegesToUser(userId, authorizationAssignmentRequest);
        return ResponseEntity.ok(userDto);
    }
}
