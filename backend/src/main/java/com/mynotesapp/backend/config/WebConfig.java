package com.mynotesapp.backend.config;

import com.mynotesapp.backend.interceptor.AddTokenToHeaderInterceptor;
import com.mynotesapp.backend.jwt.JwtToken;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtToken jwtToken;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AddTokenToHeaderInterceptor(jwtToken)).addPathPatterns("/backend/users/login");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
