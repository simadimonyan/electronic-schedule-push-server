package com.mycollege.push.application.ports.input.dto;

import lombok.Data;

@Data
public class PushTokenRequest {
    private String macAddress;
    private String phoneModel;
    private String pushToken;
    private String accessToken;
}
