package com.example.week4.postapplication.Advice;

import com.example.week4.postapplication.Exceptions.JwtAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence( HttpServletRequest request,
                          HttpServletResponse response,
                          AuthenticationException authEx)
            throws IOException {
        // extracting JwtAuthenticationException stored in request
//        JwtAuthenticationException jwtException = (JwtAuthenticationException) request.getAttribute("jwtException");
//        String message = jwtException != null ? jwtException.getMessage() : authEx.getMessage();

        String message = authEx.getLocalizedMessage();
        System.out.println("Entry point message: " + message);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
//        response.getWriter().write("""
//            {
//              "error": "invalid_token",
//              "message": "JWT is invalid or expired"
//            }
//        """);
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now());

        final OutputStream out = response.getOutputStream();
        mapper.writeValue(out, body);
        out.flush();
    }


    private final ObjectMapper mapper;
    }



