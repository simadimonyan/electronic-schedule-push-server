package com.mycollege.push.infrastructure.adapters.output.persistance.connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mycollege.push.infrastructure.configuration.DatabaseConfiguration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MongoConnection {

    private final MongoClient connection;

    @Autowired
    public MongoConnection(DatabaseConfiguration configuration) {
        this.connection = MongoClients.create(configuration.getConnection());
    }

}
