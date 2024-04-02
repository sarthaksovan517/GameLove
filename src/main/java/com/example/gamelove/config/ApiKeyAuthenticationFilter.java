package com.example.gamelove.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import java.io.IOException;
import java.util.Collections;

@Component
@Log4j2
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    @Value("${api.key}")
    private String apiKey;
    @Value("${api.secret}")
    private String apiSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestApiKey = request.getHeader("X-API-KEY");
        String requestApiSecret = request.getHeader("X-API-SECRET");
        System.out.println("requestApiKey "+requestApiKey);
        System.out.println("requestApiSecret "+requestApiSecret);
        if (apiKey.equals(requestApiKey) && apiSecret.equals(requestApiSecret)) {
            var authenticationToken = new UsernamePasswordAuthenticationToken(requestApiKey,
                    requestApiKey, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            logger.info("Authenticated successfully using api-key and secret");
            filterChain.doFilter(request, response);
        } else {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or incorrect Api Key!!!");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {

        String path = request.getRequestURI();
        return path.contains("/h2-console");
    }
}
