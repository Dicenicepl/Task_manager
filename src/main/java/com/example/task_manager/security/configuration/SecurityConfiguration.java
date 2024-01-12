package com.example.task_manager.security.configuration;

import com.example.task_manager.security.filter.TokenAuthFilter;
import com.example.task_manager.tokens.services.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final TokenService tokenService;

    public SecurityConfiguration(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new TokenAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests().requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
        return http.build();
    }
}
