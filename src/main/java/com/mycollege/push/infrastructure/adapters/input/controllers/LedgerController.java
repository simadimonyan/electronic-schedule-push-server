package com.mycollege.push.infrastructure.adapters.input.controllers;

import com.mycollege.push.infrastructure.adapters.input.dto.PushTokenRequest;
import com.mycollege.push.infrastructure.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedgerController {

    private final JwtUtil jwt;

    public LedgerController(JwtUtil jwt) {
        this.jwt = jwt;
    }

    @PostMapping("/ledger/pullTokenUp")
    public String pull(@RequestBody PushTokenRequest request) {

        if (jwt.validateToken(request.getAccessToken())) {
            return "true";
        }

        return "Forbidden";
    }

}


