package com.example.week5.postapplication.Config;

import com.example.week5.postapplication.Advice.JwtAuthEntryPoint;
import com.example.week5.postapplication.Filter.JwtAuthFilter;
import com.example.week5.postapplication.Filter.HttpLoggingFilter;
import com.example.week5.postapplication.Handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.week5.postapplication.Entities.Enums.Role.ADMIN;
import static com.example.week5.postapplication.Entities.Enums.Role.CREATOR;

@Configuration @EnableWebSecurity
@RequiredArgsConstructor
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
                        .requestMatchers(HttpMethod.GET,"/post/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/post/**").hasAnyRole(ADMIN.name(), CREATOR.name())
                        .requestMatchers(HttpMethod.DELETE,"/post/**").hasRole(ADMIN.name())
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
