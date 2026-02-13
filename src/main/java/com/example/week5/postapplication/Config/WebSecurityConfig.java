package com.example.week5.postapplication.Config;

import com.example.week5.postapplication.Advice.JwtAuthEntryPoint;
import com.example.week5.postapplication.Entities.Enums.Permission;
import com.example.week5.postapplication.Filter.JwtAuthFilter;
import com.example.week5.postapplication.Filter.HttpLoggingFilter;
import com.example.week5.postapplication.Handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.week5.postapplication.Entities.Enums.Role.*;
import static com.example.week5.postapplication.Entities.Enums.Permission.*;

@Configuration @EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final HttpLoggingFilter loggingFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private static final String[] publicRoutes = {
            "/auth/**","/error","/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/post/**").authenticated()

                        .requestMatchers("/admin/**").hasRole(ADMIN.name())

                        .anyRequest().authenticated() )
                .csrf(csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, HttpLoggingFilter.class)
                .oauth2Login( oAuth2Config -> oAuth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                )
//                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
                ;
//                .formLogin(Customizer.withDefaults());

        //Due to some reason exceptionHandling method here , causing problems with oauth2login, thus commented out

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
