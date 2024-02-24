package com.example.task_manager.security.configuration;

import com.example.task_manager.logs.services.LogService;
import com.example.task_manager.roles.services.RoleService;
import com.example.task_manager.tokens.services.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final TokenService tokenService;
    private final RoleService roleService;
    private final LogService logService;

    public SecurityConfiguration(TokenService tokenService, RoleService roleService, LogService logService) {
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.logService = logService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/login").permitAll()
                                .requestMatchers("/api/*").authenticated()
                                .anyRequest().permitAll()
                );
//                .addFilterBefore(new TokenAuthFilter(tokenService, roleService, logService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
