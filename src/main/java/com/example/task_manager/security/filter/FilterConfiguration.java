package com.example.task_manager.security.filter;

import com.example.task_manager.logs.services.LogService;
import com.example.task_manager.roles.services.RoleService;
import com.example.task_manager.tokens.services.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    private final TokenService tokenService;
    private final RoleService roleService;
    private final LogService logService;

    public FilterConfiguration(TokenService tokenService, RoleService roleService, LogService logService) {
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.logService = logService;
    }

    @Bean
    public FilterRegistrationBean<TokenAuthFilter> logFilter(){
        FilterRegistrationBean<TokenAuthFilter> regBean = new FilterRegistrationBean<>();

        regBean.setFilter(new TokenAuthFilter(tokenService, roleService, logService));
        regBean.addUrlPatterns("/api/*");
        regBean.setName("TokenAuthFilter");
        regBean.setOrder(1);

        return regBean;
    }
}
