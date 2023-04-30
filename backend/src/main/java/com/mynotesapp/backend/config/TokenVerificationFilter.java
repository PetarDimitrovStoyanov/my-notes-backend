package com.mynotesapp.backend.config;

import com.mynotesapp.backend.domain.service.user.UserService;
import com.mynotesapp.backend.exception.TokenNotValidException;
import com.mynotesapp.backend.jwt.JwtToken;
import com.mynotesapp.backend.util.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class TokenVerificationFilter extends OncePerRequestFilter {

    private final JwtToken jwtToken;

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);

        String username = null;
        String jwt = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith(Constants.BEARER_SPACE)) {
                jwt = authorizationHeader.replace(Constants.BEARER_SPACE, "");
                username = jwtToken.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userService.loadUserByUsername(username);
                if (jwtToken.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        } catch (Exception exception) {
            if (exception.getMessage().contains("expired")) {
                HttpStatus status = HttpStatus.CONFLICT;
                String message = "Token has expired";
                response.setStatus(status.value());
                response.getWriter().write("{\"error\": \"" + message + "\"}");
            } else {
                HttpStatus status = HttpStatus.UNAUTHORIZED;
                response.setStatus(status.value());
                response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
            }
            response.setContentType("application/json");

            return;
        }
        filterChain.doFilter(request, response);
    }
}
