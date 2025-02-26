package com.mycollege.push.infrastructure.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 * RuStore Configuration properties
 */
@Getter
@ConfigurationProperties(prefix = "vk.rustore")
public class RuStoreConfiguration {

    private final String projectId;
    private final String serviceToken;

    @ConstructorBinding
    public RuStoreConfiguration(String projectId, String serviceToken) {
        this.projectId = projectId;
        this.serviceToken = serviceToken;
    }

}
