package pushserver.application.ports.output.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import pushserver.infrastructure.adapters.output.persistance.entities.TokenEntity;

public interface PushRepository extends MongoRepository<TokenEntity, String> {
    TokenEntity findByPushToken(String pushToken);
}
