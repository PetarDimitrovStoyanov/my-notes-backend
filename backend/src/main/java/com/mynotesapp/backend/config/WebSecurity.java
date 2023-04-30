package com.mynotesapp.backend.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurity {

    private final TokenVerificationFilter tokenVerificationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/backend/users/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/backend/users/register").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(tokenVerificationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsFilter(), CorsFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Access-Control-Allow-Headers");
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {

        return new CorsFilter(corsConfigurationSource());
    }
}


