package com.mycollege.push.domain.model;

import lombok.Data;

@Data
public class Token {

    private String macAddress;
    private String phoneModel;
    private String pushToken;

    public Token(String macAddress, String phoneModel, String pushToken) {
        this.macAddress = macAddress;
        this.phoneModel = phoneModel;
        this.pushToken = pushToken;
    }

}
