package com.example.usersample.common.config.security;

import com.example.usersample.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;

    /*
    springboot 3.x 버전에서 security 버전이 6.x 로 변경됨.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/login", "/login*").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll();
                    authorizeRequests.requestMatchers("/api/v1/users/**").hasAnyRole("USER", "ADMIN");
                    authorizeRequests.requestMatchers("/swagger*/**").permitAll();
                    authorizeRequests.requestMatchers("/api/v1/users/**").hasAnyRole("USER", "ADMIN");
                    authorizeRequests.anyRequest().permitAll();
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManagerBean()))
                .addFilter(new JwtProvider(authenticationManagerBean(), userRepository))
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
