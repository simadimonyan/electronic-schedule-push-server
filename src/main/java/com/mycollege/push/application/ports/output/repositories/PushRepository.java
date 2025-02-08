package com.mycollege.push.application.ports.output.repositories;

import com.mycollege.push.infrastructure.adapters.output.persistance.entities.TokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PushRepository extends MongoRepository<TokenEntity, String> {
    // spring implementation generation
}
