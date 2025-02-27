package pushserver.application.ports.input.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String secret;
}
