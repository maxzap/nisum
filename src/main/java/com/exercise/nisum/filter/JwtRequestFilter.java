package com.exercise.nisum.filter;

import com.exercise.nisum.service.MyUserDetailsService;
import com.exercise.nisum.util.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    MyUserDetailsService userDetailsService;

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
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error al evaluar el token");
                }
            }

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);

                if (!jwtUtilService.validateToken(jwt, userDetails)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido");
                } else {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } else if (jwt == null) {
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "No estan completos todos los headers requeridos");
            }
        }
        try {
            chain.doFilter(request, response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Stream.of("/user/create", "/user/authenticate" )
                .anyMatch(url -> request.getRequestURI().startsWith(url));
    }
}
