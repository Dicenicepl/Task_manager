package com.example.task_manager.security.configuration;

import com.example.task_manager.roles.repositories.RoleRepository;
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
    private final RoleRepository roleRepository;

    public SecurityConfiguration(TokenService tokenService, RoleRepository roleRepository) {
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    //todo check why role isn`t checked by Spring Security,
    // example: user_role = ADMIN - working| user_role = USER - working
    // needed result user_role = ADMIN - working | user_role = USER - block
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/s/***").permitAll()
                                .requestMatchers("/api/u/list/users/").hasRole("ADMIN")
//                                .anyRequest().hasRole("ADMIN")
//                                .anyRequest().authenticated()
                )
                .addFilterBefore(new TokenAuthFilter(tokenService, roleRepository), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
