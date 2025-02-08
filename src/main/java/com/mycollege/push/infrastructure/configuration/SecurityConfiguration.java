package com.mycollege.push.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "security")
public class SecurityConfiguration {

    private final String secret;

    @ConstructorBinding
    public SecurityConfiguration(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

}
