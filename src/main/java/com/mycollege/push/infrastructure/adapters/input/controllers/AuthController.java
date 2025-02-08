package com.mycollege.push.infrastructure.adapters.input.controllers;

import com.mycollege.push.application.ports.input.dto.AuthRequest;
import com.mycollege.push.infrastructure.configuration.SecurityConfiguration;
import com.mycollege.push.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final String SECRET;
    private final JwtUtil jwt;

    @Autowired
    public AuthController(SecurityConfiguration properties, JwtUtil jwt) {
        this.SECRET = properties.getSecret();
        this.jwt = jwt;
    }

    @PostMapping("/auth")
    public String generateToken(@RequestBody AuthRequest request) {
        if (request.getSecret().equals(SECRET)) {
            return jwt.createAccessToken();
        }
        return "Forbidden";
    }

}
