package com.myblog7.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig {
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()                 //this will prevent from csrf attack like if someone says to click the link and we clicked it and money gone to fraud this is why we use this in production environment i will .csrf().enable() it
                    .authorizeRequests()//it means we should authorize
                    .anyRequest()//any request which is coming
                    .authenticated()
                    .and()
                    .httpBasic();
        }
    }

}
