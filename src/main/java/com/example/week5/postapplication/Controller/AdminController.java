package com.example.week5.postapplication.Controller;

import com.example.week5.postapplication.DTO.UserDto;
import com.example.week5.postapplication.Entities.Enums.Role;
import com.example.week5.postapplication.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/{userid}")
    ResponseEntity<UserDto> assignRoles(@PathVariable(name = "userid") Long userId,
                                        @RequestBody Set<Role> roleSet){

        UserDto userDto = adminService.assignRoles(userId, roleSet);
        return ResponseEntity.ok(userDto);
    }
}
