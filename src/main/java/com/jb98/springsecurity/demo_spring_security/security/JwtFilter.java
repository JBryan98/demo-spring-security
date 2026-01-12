package com.jb98.springsecurity.demo_spring_security.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb98.springsecurity.demo_spring_security.exception.ErrorCode;
import com.jb98.springsecurity.demo_spring_security.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.EXPIRED_JWT;
import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.INVALID_JWT;
import static com.jb98.springsecurity.demo_spring_security.exception.ErrorCode.USER_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * This filter is executed once per request.
     * It validates the JWT token and sets the authentication
     * in the SecurityContext if the token is valid.
     */
    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        // Skip JWT validation for authentication endpoints
        if (request.getServletPath().startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }


        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String username;

        // If there is no Authorization header, or it does not start with "Bearer ",
        // continue the filter chain without authentication
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the header
        jwt = authHeader.substring(7);
        try {
            username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load the user from the database
                var userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // Attach request details (IP, session, etc.)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Set authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (UsernameNotFoundException ex) {
            writeJwtErrorResponse(response, USER_NOT_FOUND);
            return;
        } catch (ExpiredJwtException ex) {
            writeJwtErrorResponse(response, EXPIRED_JWT);
            return;
        } catch (JwtException ex) {
            writeJwtErrorResponse(response, INVALID_JWT);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void writeJwtErrorResponse(HttpServletResponse response, ErrorCode error) throws IOException {
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        var body = ErrorResponse
                .builder()
                .title(error.getTitle())
                .error(error.name())
                .detail(error.getDefaultMessage())
                .build();
        new ObjectMapper().writeValue(response.getWriter(), body);
    }
}

