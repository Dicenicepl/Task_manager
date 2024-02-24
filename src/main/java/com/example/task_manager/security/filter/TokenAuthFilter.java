package com.example.task_manager.security.filter;


import com.example.task_manager.logs.services.LogService;
import com.example.task_manager.roles.entities.Role;
import com.example.task_manager.roles.services.RoleService;
import com.example.task_manager.security.cachedrequest.CachedBodyHttpServletRequest;
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
    private final RoleService roleService;
    private final LogService logService;

    public TokenAuthFilter(TokenService tokenService, RoleService roleService, LogService logService) {
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.logService = logService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String email = "REQUEST DOESN`T HAVE WORKING TOKEN ";
        if (tokenService.isNotExpired(token)) {
            email = tokenService.findAssignedEmailByToken(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(email, "Credentials", findRolesByEmail(email));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        System.out.println("ACTIVE FILTER");
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
        logService.creatingFirstConfiguration(cachedBodyHttpServletRequest, email);
        filterChain.doFilter(cachedBodyHttpServletRequest, response);

    }


    private Collection<? extends GrantedAuthority> findRolesByEmail(String email) {
        Collection<Role> userRoles = roleService.findRolesByEmail(email);
        return convertToAuthorities(userRoles);
    }

    private Collection<? extends GrantedAuthority> convertToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toList());
    }
}
