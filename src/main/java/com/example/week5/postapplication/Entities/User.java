package com.example.week5.postapplication.Entities;


import com.example.week5.postapplication.Entities.Enums.Plan;
import com.example.week5.postapplication.Entities.Enums.Role;
import com.example.week5.postapplication.Entities.Enums.Permission;
import com.example.week5.postapplication.Utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String email;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private Plan plan;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    private Set<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<SimpleGrantedAuthority> authorities =  roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
//                .collect(Collectors.toSet());
//
//        permissions.forEach(permission ->
//                authorities.add( new SimpleGrantedAuthority(permission.name()) ));

        //Using PermissionMapping class for predefined role permission mappings
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach( role -> {
            Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritiesForARole(role);
            authorities.addAll(permissions);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );

        return authorities;

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
