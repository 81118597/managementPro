package com.itlike.system.springsecurity.config;

import com.itlike.system.springsecurity.handler.CustomAccessDeineHandler;
import com.itlike.system.springsecurity.handler.CustomAuthenticationFailureHandler;
import com.itlike.system.springsecurity.handler.CustomAuthenticationSuccessHandler;
import com.itlike.system.springsecurity.handler.CustomizeAuthenticationEntryPoint;
import com.itlike.system.springsecurity.handler.code.ImageCodeValidateFilter;
import com.itlike.system.springsecurity.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity//安全配置类
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeineHandler customAccessDeineHandler;
    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .cors().and()
                .formLogin()
                .loginProcessingUrl("/api/user/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/login","/api/user/code/image","/swagger-ui.html","/swagger-resources/**","/*/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customizeAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeineHandler);
    }
}
