package com.coface.lesson5.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ChainOfFilterSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public ChainOfFilterSecurityConfig(
            AuthenticationProvider authenticationProvider,
            AuthenticationEntryPoint authenticationEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(
                        (customizer) ->
                            customizer.sessionCreationPolicy(
                                    SessionCreationPolicy.STATELESS
                            )
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        (authorize) ->
                                authorize
                                        .requestMatchers(
                                                HttpMethod.POST,
                                                "/api/v1/usuario",
                                                "/api/v1/autenticacion/login"
                                        )
                                        .permitAll()
                                        .requestMatchers(
                                                HttpMethod.GET,
                                                "/api/v1/usuario",
                                                "/api/v1/usuario/{id}",
                                                "/api/v1/usuario/paginado",
                                                "/api/v1/usuario/reducido"
                                        )
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .exceptionHandling(
                        (customizer) ->
                                customizer
                                        .authenticationEntryPoint(
                                                authenticationEntryPoint
                                        )
                )
                .build();
    }
}
