package com.nithingodugu.hospitalManagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

         return httpSecurity
                 .csrf(csrf -> csrf.disable())
                 .sessionManagement(sessionConfig ->
                         sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/public/**").permitAll()
                         .requestMatchers("/auth/**").permitAll()
                         .anyRequest().authenticated()
                  )
                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                 .formLogin(Customizer.withDefaults())
                 .build();

    }
}
