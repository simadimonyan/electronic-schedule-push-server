package pushserver.infrastructure.adapters.output.persistance.connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pushserver.infrastructure.configuration.DatabaseConfiguration;

@Getter
@Component
public class MongoConnection {

    private final MongoClient connection;

    @Autowired
    public MongoConnection(DatabaseConfiguration configuration) {
        this.connection = MongoClients.create(configuration.getConnection());
    }

}
