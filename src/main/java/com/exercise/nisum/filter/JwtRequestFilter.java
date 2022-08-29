package com.exercise.nisum.filter;

import com.exercise.nisum.model.UserModel;
import com.exercise.nisum.service.UserService;
import com.exercise.nisum.util.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    UserService userService;

    @Autowired
    JwtToken jwtUtilService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String userId = null;
        String jwt = null;

        if(!shouldNotFilter(request)) {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    jwt = authorizationHeader.substring(7);
                    userId = jwtUtilService.extractUserId(jwt);

                } catch (Exception exc) {
                    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al evaluar el token");
                };
            }

            if (userId != null) {

                if (!jwtUtilService.validateToken(jwt)) {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token invalido");
                }
            } else if (jwt == null) {
                response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), "No estan completos todos los headers requeridos");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return Stream.of("/app/user/create")
                .anyMatch(url -> request.getRequestURI().startsWith(url));
    }
}
