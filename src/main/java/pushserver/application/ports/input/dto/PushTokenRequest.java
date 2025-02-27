package pushserver.application.ports.input.dto;

import lombok.Data;

@Data
public class PushTokenRequest {
    private String os;
    private String phoneModel;
    private String pushToken;
    private String accessToken;
}
