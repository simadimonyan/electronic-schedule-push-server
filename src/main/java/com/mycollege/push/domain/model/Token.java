package com.mycollege.push.domain.model;

import lombok.Data;

@Data
public class Token {

    private String os;
    private String phoneModel;
    private String pushToken;

    public Token(String os, String phoneModel, String pushToken) {
        this.os = os;
        this.phoneModel = phoneModel;
        this.pushToken = pushToken;
    }

}
