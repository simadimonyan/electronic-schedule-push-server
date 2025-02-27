package pushserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pushserver.infrastructure.configuration.DatabaseConfiguration;
import pushserver.infrastructure.configuration.RuStoreConfiguration;
import pushserver.infrastructure.configuration.SecurityConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({RuStoreConfiguration.class, SecurityConfiguration.class, DatabaseConfiguration.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
