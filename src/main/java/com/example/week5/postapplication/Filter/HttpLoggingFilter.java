package com.example.week5.postapplication.Filter;

import com.example.week5.postapplication.Exceptions.ResourceNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            //directly using request method, can consume the inputStream, thus cannot get request body
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

            log.info("==== REQUEST ====");
            log.info("Request - Method: {}, URI: {}", requestWrapper.getMethod(), requestWrapper.getRequestURI());
            String headerInformation = getHeaders(requestWrapper);
            log.debug("Headers: {}", headerInformation);

            Long startTime = System.currentTimeMillis();

            filterChain.doFilter(requestWrapper,responseWrapper);

            Long duration = System.currentTimeMillis() - startTime;

            //request/requestWrapper should also be read after doFilter , as request only has a pointer to the
            // body stream, not the body itself. After doFilter, we can get the content (although not preferred to save
            // direct content in logs, to avoid
            log.debug("REQUEST - Body: {}", requestWrapper.getContentAsString());

            log.info("==== RESPONSE ====");
            log.info("response - status: {}, duration: {} ms",response.getStatus(), duration);
            log.debug("RESPONSE - Body: {}",new String(responseWrapper.getContentAsByteArray()));

            responseWrapper.copyBodyToResponse();
        }
        catch (Exception e){
            log.error("exception in logging filter");
            throw new ResourceNotFoundException("pata nahi what happened");
        }
    }

    //getting all header names and values, except authorization
    private String getHeaders(ContentCachingRequestWrapper request){

//        Iterator<String> headerNames = request.getHeaderNames().asIterator();
//        StringBuilder headerInformation = new StringBuilder();
//        while(headerNames.hasNext()){
//
//            String name = headerNames.next();
//            if(name.equalsIgnoreCase("authorization"))
//                continue;
//            headerInformation.append(name)
//                    .append(" = ")
//                    .append( request.getHeader(name) )
//                    .append(" , ");
//        }
//        return headerNames.toString();

        return Collections.list(request.getHeaderNames())
                .stream()
                .filter(name -> !name.equalsIgnoreCase("authorization"))
                .map(name -> name+": "+request.getHeader(name) )
                .collect(Collectors.joining(", "));
    }
}
