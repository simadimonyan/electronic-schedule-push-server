package pushserver.infrastructure.adapters.input.controllers;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pushserver.application.ports.input.dto.AuthRequest;
import pushserver.domain.model.Token;
import pushserver.domain.usecase.PushService;
import pushserver.infrastructure.configuration.RuStoreConfiguration;
import pushserver.infrastructure.configuration.SecurityConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class PushController {

    private final String PROJECT_ID;
    private final String SERVICE_TOKEN;
    private final String SECRET;

    private final PushService service;
    private final Map<String, String> progressMap = new ConcurrentHashMap<>();
    private final Logger LOGGER = LoggerFactory.getLogger(PushController.class);

    @Autowired
    public PushController(RuStoreConfiguration properties, SecurityConfiguration conf, PushService service) {
        this.PROJECT_ID = properties.getProjectId();
        this.SERVICE_TOKEN = properties.getServiceToken();
        this.SECRET = conf.getSecret();
        this.service = service;
    }

    @GetMapping("/push/progress")
    public String progress() {
        long successful = progressMap.values().stream().filter("SUCCESS"::equals).count();
        long failed = progressMap.values().stream().filter("FAILED"::equals).count();
        long pending = progressMap.values().stream().filter("PENDING"::equals).count();

        return String.format("Progress: Successful: %d, Failed: %d, Pending: %d", successful, failed, pending);
    }

    @PostMapping("/push/publication")
    public String push(@org.springframework.web.bind.annotation.RequestBody AuthRequest request) {
        LOGGER.info("Received push publication request");

        if (request.getSecret().equals(SECRET)) {
            LOGGER.info("Authorization successful. Starting push notifications.");
            try {
                ArrayList<ArrayList<Token>> cluster = service.getSubscribed();
                LOGGER.info("Retrieved subscribed tokens. Cluster size: {}", cluster.size());

                try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
                    for (ArrayList<Token> batch : cluster) {
                        LOGGER.info("Submitting batch with size: {}", batch.size());
                        executor.submit(() -> sendBatch(batch));
                    }
                    executor.shutdown();
                }
                return "Push notifications started. Track progress via '/push/progress'";
            } catch (Exception e) {
                LOGGER.error("Error during push notification process: {}", e.getMessage(), e);
                return "Error: " + e.getMessage();
            }
        }

        LOGGER.warn("Authorization failed. Invalid secret.");
        return "Unauthorized";
    }


    private void sendBatch(ArrayList<Token> batch) {
        LOGGER.info("Processing batch with size: {}", batch.size());

        OkHttpClient client = new OkHttpClient();

        for (Token token : batch) {
            LOGGER.info("Preparing notification for token: {}", token.getPushToken());
            progressMap.put(token.getPushToken(), "PENDING");
            LOGGER.info("Token {} status updated to PENDING", token.getPushToken());

            String url = "https://vkpns.rustore.ru/v1/projects/" + PROJECT_ID + "/messages:send";
            String body = "This is a notification message!";
            String title = "Message";

            String json = String.format("""
                {
                   "message" :{
                      "token" : "%s",
                      "notification" :{
                        "body" : "%s",
                        "title" : "%s"
                      }
                   }
                }
                """, token.getPushToken(), body, title);

            RequestBody requestBody = RequestBody.create(
                    json,
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + SERVICE_TOKEN)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    progressMap.put(token.getPushToken(), "FAILED");
                    LOGGER.error("Failed to send notification to token: {}. Error: {}", token.getPushToken(), e.getMessage(), e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
                    try (response) {
                        if (response.isSuccessful()) {
                            progressMap.put(token.getPushToken(), "SUCCESS");
                            LOGGER.info("Notification successfully sent to token: {}", token.getPushToken());
                        } else {
                            progressMap.put(token.getPushToken(), "FAILED");
                            LOGGER.error("Failed to send notification to token: {}. Response code: {}, Message: {}",
                                    token.getPushToken(), response.code(), response);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error processing response for token: {}. Error: {}", token.getPushToken(), e.getMessage(), e);
                    }
                }
            });
        }
        LOGGER.info("Batch processing completed.");
    }
}
