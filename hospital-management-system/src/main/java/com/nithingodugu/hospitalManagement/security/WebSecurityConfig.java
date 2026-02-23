package com.nithingodugu.hospitalManagement.security;

import com.nithingodugu.hospitalManagement.entity.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static com.nithingodugu.hospitalManagement.entity.type.RoleType.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

         return httpSecurity
                 .csrf(csrf -> csrf.disable())
                 .sessionManagement(sessionConfig ->
                         sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/public/**", "/auth/**").permitAll()
                         .requestMatchers("/admin/**").hasRole(ADMIN.name())
                         .requestMatchers("/doctors/**").hasAnyRole(ADMIN.name(), DOCTOR.name())
                         .anyRequest().authenticated()
                  )
                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                 .oauth2Login(oauth2 ->oauth2
                         .failureHandler(
                                 ((request, response, exception) -> {
                                     log.debug("user oauth2 login error : {}", exception.getMessage());
                                     handlerExceptionResolver.resolveException(request, response,null, exception);
                                 }))

                         .successHandler(oAuth2SuccessHandler)
                 )
                 .exceptionHandling(exceptionHandlingConfigurer ->
                         exceptionHandlingConfigurer.accessDeniedHandler(
                                 (request, response, accessDeniedException) ->{
                                     handlerExceptionResolver.resolveException(request, response,null, accessDeniedException);
                                 })
                         )

//                 .formLogin(Customizer.withDefaults())
                 .build();

    }
}
