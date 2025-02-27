package pushserver.infrastructure.adapters.input.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pushserver.application.ports.input.dto.AuthRequest;
import pushserver.infrastructure.configuration.SecurityConfiguration;
import pushserver.infrastructure.security.JwtUtil;

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
