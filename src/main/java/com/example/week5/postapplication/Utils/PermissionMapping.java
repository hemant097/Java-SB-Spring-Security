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


    static Set<Permission> userPermissions = Set.of(USER_READ, POST_READ, USER_UPDATE);
    static Set<Permission> creatorPermissions = Set.of(POST_CREATE, POST_UPDATE, POST_DELETE,POST_READ, USER_UPDATE, USER_READ);
    static Set<Permission> adminPermissions = Set.of(POST_CREATE, POST_UPDATE, POST_DELETE, POST_READ, USER_CREATE, USER_DELETE, USER_UPDATE, USER_READ);

    private static final Map<Role, Set<Permission>> permissionData = Map.of(
            USER, userPermissions,
            CREATOR, creatorPermissions,
            ADMIN, adminPermissions
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForARole(Role role){

        return permissionData.get(role).stream()
                .map( permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
