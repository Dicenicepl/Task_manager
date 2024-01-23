package com.example.task_manager.security.configuration;

import com.example.task_manager.roles.services.RoleService;
import com.example.task_manager.security.filter.TokenAuthFilter;
import com.example.task_manager.tokens.services.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final TokenService tokenService;
    private final RoleService roleService;

    public SecurityConfiguration(TokenService tokenService, RoleService roleService) {
        this.tokenService = tokenService;
        this.roleService = roleService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
//                                .requestMatchers("/login").permitAll()
//                                .requestMatchers("/register").permitAll()
//                                .requestMatchers("/api/s/***").permitAll()
//                                .anyRequest().hasRole("USER")
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new TokenAuthFilter(tokenService, roleService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
