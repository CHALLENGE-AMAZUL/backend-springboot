package com.amazul.fiap.api_springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    @Profile("azure")
    UserDetailsService admin(@Value("${app.admin.username}") String username,
                             @Value("${app.admin.password}") String password) {
        if (username.isBlank() || password.length() < 16) {
            throw new IllegalArgumentException("Defina usuário e senha de pelo menos 16 caracteres para Azure.");
        }
        return new InMemoryUserDetailsManager(User.withUsername(username)
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode(password)).roles("EDITOR").build());
    }

    @Bean
    @Profile("azure")
    SecurityFilterChain azure(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console", "/h2-console/**").denyAll()
                        .requestMatchers(HttpMethod.GET, "/api/**", "/actuator/health", "/actuator/health/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/**").hasRole("EDITOR")
                        .anyRequest().denyAll())
                .httpBasic(Customizer.withDefaults()).build();
    }

    @Bean
    @Profile("!azure")
    SecurityFilterChain local(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).build();
    }
}
