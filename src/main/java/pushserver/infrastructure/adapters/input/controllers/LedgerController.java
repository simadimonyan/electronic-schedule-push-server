package pushserver.infrastructure.adapters.input.controllers;

import com.mycollege.push.application.ports.input.dto.PushTokenRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pushserver.domain.usecase.PushService;
import pushserver.infrastructure.security.JwtUtil;

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
                    request.getOs(),
                    request.getPhoneModel(),
                    request.getPushToken()
            );

            return result ? "Successfully subscribed!"  : "Token already exists!" ;
        }

        return "Forbidden";
    }

}


