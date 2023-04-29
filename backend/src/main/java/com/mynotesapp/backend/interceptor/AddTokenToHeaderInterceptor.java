package com.mynotesapp.backend.interceptor;

import com.mynotesapp.backend.jwt.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
public class AddTokenToHeaderInterceptor implements HandlerInterceptor {

    private final JwtToken jwtToken;

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {

        String userEmail = request.getUserPrincipal().getName();
        String token = jwtToken.generateByUserCredentials(userEmail);
        response.addHeader("Authorization", String.format("Bearer %s", token));

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
