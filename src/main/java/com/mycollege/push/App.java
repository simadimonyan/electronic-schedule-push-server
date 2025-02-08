package com.mycollege.push;

import com.mycollege.push.infrastructure.configuration.DatabaseConfiguration;
import com.mycollege.push.infrastructure.configuration.RuStoreConfiguration;
import com.mycollege.push.infrastructure.configuration.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RuStoreConfiguration.class, SecurityConfiguration.class, DatabaseConfiguration.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
