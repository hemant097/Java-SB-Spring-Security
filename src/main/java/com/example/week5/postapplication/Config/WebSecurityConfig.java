package com.example.week5.postapplication.Config;

import com.example.week5.postapplication.Advice.JwtAuthEntryPoint;
import com.example.week5.postapplication.Filter.JwtAuthFilter;
import com.example.week5.postapplication.Filter.HttpLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final HttpLoggingFilter loggingFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/post","/auth/**").permitAll()
//                        .requestMatchers("/post/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated() )
                .csrf(csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, HttpLoggingFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint));
//                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

//    @Bean Configuring users for in memory user details manager
//    UserDetailsService inMemoryUserDetailsService(){
//        UserDetails normalUser = User.withUsername("hemant")
//                .password(passwordEncoder().encode("hemu123"))
//                .roles("USER").build();
//
//        UserDetails adminUser = User.withUsername("admin").password(passwordEncoder().encode("admin"))
//                .roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(adminUser,normalUser);
//    }


    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
    }

}
