package com.mycollege.push.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 * RuStore Configuration properties
 */
@ConfigurationProperties(prefix = "vk.rustore")
public class RuStoreConfiguration {

    private final String projectId;
    private final String serviceToken;

    @ConstructorBinding
    public RuStoreConfiguration(String projectId, String serviceToken) {
        this.projectId = projectId;
        this.serviceToken = serviceToken;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getServiceToken() {
        return serviceToken;
    }

}
