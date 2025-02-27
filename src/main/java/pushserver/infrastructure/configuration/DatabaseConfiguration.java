package pushserver.infrastructure.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "mongodb")
public class DatabaseConfiguration {

    private final String connection;

    @ConstructorBinding
    public DatabaseConfiguration(String connection) {
        this.connection = connection;
    }

}
