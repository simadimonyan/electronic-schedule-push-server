package com.mycollege.push.infrastructure.adapters.input.controllers;

import com.mycollege.push.application.ports.input.dto.PushTokenRequest;
import com.mycollege.push.domain.usecase.PushService;
import com.mycollege.push.infrastructure.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedgerController {

    private final JwtUtil jwt;
    private final PushService service;

    public LedgerController(JwtUtil jwt, PushService service) {
        this.jwt = jwt;
        this.service = service;
    }

    @PostMapping("/ledger/pullTokenUp")
    public String pull(@RequestBody PushTokenRequest request) {

        if (jwt.validateToken(request.getAccessToken())) {

            Boolean result = service.subscribe(
                    request.getMacAddress(),
                    request.getPhoneModel(),
                    request.getPushToken()
            );

            return result ? "Successfully subscribed!"  : "500" ;
        }

        return "Forbidden";
    }

}


