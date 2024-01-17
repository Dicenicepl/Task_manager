package com.example.task_manager.security.filter;


import com.example.task_manager.roles.entities.Role;
import com.example.task_manager.roles.repositories.RoleRepository;
import com.example.task_manager.tokens.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;


@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final RoleRepository roleRepository;


    public TokenAuthFilter(TokenService tokenService, RoleRepository roleRepository) {
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (tokenService.isNotExpired(token)) {
            String email = tokenService.findAssignedEmailByToken(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(email, "Credentials", findRolesByUsername(email));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }


    private Collection<? extends GrantedAuthority> findRolesByUsername(String username) {
        Collection<Role> userRoles = roleRepository.findRolesByEmail(username);
        System.out.println("Roles for user " + username + ": " + userRoles);
        return convertToAuthorities(userRoles);
    }

    private Collection<? extends GrantedAuthority> convertToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toList());
    }
}
