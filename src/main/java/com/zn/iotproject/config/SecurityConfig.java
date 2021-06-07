package com.zn.iotproject.config;

import com.zn.iotproject.security.FilterSkipMatcher;
import com.zn.iotproject.security.filters.JwtAuthenticationFilter;
import com.zn.iotproject.security.filters.LoginFilter;
import com.zn.iotproject.security.handler.LoginAuthenticationFailureHandler;
import com.zn.iotproject.security.handler.LoginAuthenticationSuccessHandler;
import com.zn.iotproject.security.provider.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    @Autowired
    private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;

    protected LoginFilter loginFilter() throws Exception {
        LoginFilter filter = new LoginFilter("/api/*/login", loginAuthenticationSuccessHandler, loginAuthenticationFailureHandler);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }

    protected JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        FilterSkipMatcher matcher = new FilterSkipMatcher(Arrays.asList("POST /api/*/login", "POST /api/*/users", "GET /api/*/users/*"), "/api/*/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, null, null);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.loginAuthenticationProvider);

    }
}
