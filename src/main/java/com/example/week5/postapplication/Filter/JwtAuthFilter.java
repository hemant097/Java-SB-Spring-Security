package com.example.week5.postapplication.Filter;

import com.example.week5.postapplication.Advice.JwtAuthEntryPoint;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Exceptions.JwtAuthenticationException;
import com.example.week5.postapplication.Service.JwtService;
import com.example.week5.postapplication.Service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

//            final String requestTokenHeader = request.getHeader("Authorization");
//            if ((requestTokenHeader == null) || !requestTokenHeader.startsWith("Bearer")) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//            String token = requestTokenHeader.split("Bearer ")[1].trim();

            final String token = getJwtFromRequest(request);

            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Long userId = jwtService.getUserIdFromToken(token);
                User userEntity = userService.getUserById(userId);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                        (userEntity, null, userEntity.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            System.out.println("inside JwtAuthFilter");
            filterChain.doFilter(request, response);
        }catch (JwtException ex){
//            System.out.println("Auth before: " + SecurityContextHolder.getContext().getAuthentication());
            System.out.println("JWT Exception: " + ex.getLocalizedMessage());

//            JwtAuthenticationException jwtAuthEx = new JwtAuthenticationException(ex.getLocalizedMessage());
//            request.setAttribute("jwtException", jwtAuthEx);
//            throw jwtAuthEx;

            JwtAuthenticationException authEx =
                    new JwtAuthenticationException(ex.getLocalizedMessage());


            jwtAuthEntryPoint.commence(request, response, authEx );

        }


    }

    //find the Authorization header from request, extracts the bearer token from it, and returns if found
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
