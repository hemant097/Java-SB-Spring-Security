package com.example.week5.postapplication.Utils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.week5.postapplication.Entities.Enums.Role;
import com.example.week5.postapplication.Entities.Enums.Permission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.example.week5.postapplication.Entities.Enums.Permission.*;
import static com.example.week5.postapplication.Entities.Enums.Role.*;

public class PermissionMapping {

    private static Map<Role, Set<Permission>> permissionData = Map.of(
            USER, Set.of(USER_READ, POST_READ),
            CREATOR, Set.of(POST_CREATE, USER_CREATE, POST_UPDATE, USER_UPDATE, POST_DELETE),
            ADMIN, Set.of(POST_CREATE, POST_UPDATE, POST_DELETE, POST_READ, USER_CREATE, USER_DELETE, USER_UPDATE, USER_READ)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForARole(Role role){

        return permissionData.get(role).stream()
                .map( permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
