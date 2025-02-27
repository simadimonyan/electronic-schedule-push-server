package pushserver.infrastructure.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "security")
public class SecurityConfiguration {

    private final String secret;

    @ConstructorBinding
    public SecurityConfiguration(String secret) {
        this.secret = secret;
    }

}
