package com.example.task_manager.security.filter;


import com.example.task_manager.tokens.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


public class TokenAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;


    public TokenAuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean check = true;
        if (request.getRequestURI().equals("/login")){
            Authentication auth = new UsernamePasswordAuthenticationToken("User", "Credentials", Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
            check = false;
        } else if (check && tokenService.isNotExpired(request.getHeader("Authorization"))) {
            Authentication auth = new UsernamePasswordAuthenticationToken("User", "Credentials", Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }
}
