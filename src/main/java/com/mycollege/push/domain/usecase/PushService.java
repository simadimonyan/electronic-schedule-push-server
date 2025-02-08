package com.mycollege.push.domain.usecase;

import com.mycollege.push.application.ports.output.repositories.PushRepository;
import com.mycollege.push.domain.model.Token;
import com.mycollege.push.infrastructure.adapters.output.persistance.entities.TokenEntity;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class PushService {

    private final PushRepository repository;
    private final Logger LOGGER = LoggerFactory.getLogger(PushService.class);

    @Autowired
    public PushService(PushRepository repository) {
        this.repository = repository;
    }

    /**
     * Subscribe on notification publications
     *
     * @param macAddress device MAC address
     * @param phoneModel device model
     * @param pushToken RuStore push-token
     *
     * @return Boolean
     */
    public Boolean subscribe(String macAddress, String phoneModel, String pushToken) {
        return saveToken(new Token(macAddress, phoneModel, pushToken));
    }

    /**
     * Get subscribed tokens - 100 per batch
     *
     * @return ArrayList
     */
    public ArrayList<ArrayList<Token>> getSubscribed() {

        ArrayList<ArrayList<Token>> cluster = new ArrayList<>();
        List<TokenEntity> entities = getTokens();

        LOGGER.info("Total tokens fetched: {}", entities.size());

        ArrayList<Token> tokenBatch = new ArrayList<>();
        for (TokenEntity token : entities) {
            LOGGER.info("Processing token with pushToken: {}", token.getPushToken());

            // cluster has 100 tokens per batch
            if (tokenBatch.size() != 100) {
                tokenBatch.add(new Token(
                        token.getMacAddress(),
                        token.getPhoneModel(),
                        token.getPushToken())
                );
            } else {
                cluster.add(tokenBatch);
                tokenBatch = new ArrayList<>();
            }
        }

        if (!tokenBatch.isEmpty()) {
            cluster.add(tokenBatch);
        }

        LOGGER.info("Total batches created: {}", cluster.size());
        return cluster;
    }

    /**
     * Repository save bridge
     *
     * @param token domain abstract object
     *
     * @return Boolean
     */
    private Boolean saveToken(Token token) {

        TokenEntity entity = new TokenEntity();
        entity.setMacAddress(token.getMacAddress());
        entity.setPhoneModel(token.getPhoneModel());
        entity.setPushToken(token.getPushToken());

        try {
            repository.save(entity);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }

    }

    /**
     * Repository findAll bridge
     *
     * @return List
     */
    private List<TokenEntity> getTokens() {
        List<TokenEntity> entities = repository.findAll();
        LOGGER.info("Fetched {} tokens from the repository", entities.size());
        return entities;
    }

}
