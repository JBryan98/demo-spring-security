package com.jb98.springsecurity.demo_spring_security.auth;

import com.jb98.springsecurity.demo_spring_security.entity.User;
import com.jb98.springsecurity.demo_spring_security.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest rq) {
        log.info("authenticate {}", rq.username());
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        rq.username(),
                        rq.password()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = (User) auth.getPrincipal();
        claims.put("username", user.getUsername());
        var jwtToken = jwtService.generateToken(claims, user);
        return new AuthenticationResponse(jwtToken);
    }
}
