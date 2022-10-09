package com.exercise.nisum.config;

import com.exercise.nisum.filter.JwtRequestFilter;
import com.exercise.nisum.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String USER_CREATE = "/user/create";
    private static final String USER_AUTHENTICATE = "/user/authenticate";
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    public static final String SECURITY_CONFIG_NAME = "App Bearer token";

    private static final String[] AUTH_LIST = {
            "**/swagger-resources/**",
            "/swagger-resources/**",
            "/swagger-ui.html**",
            "/swagger-ui/**",
            "/swagger-ui*/**",
            "/webjars/**",
            "favicon.ico",
            "/v2/api-docs/**",
            "/configuration/**",
            USER_CREATE,
            USER_AUTHENTICATE
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(AUTH_LIST).permitAll().
                anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
