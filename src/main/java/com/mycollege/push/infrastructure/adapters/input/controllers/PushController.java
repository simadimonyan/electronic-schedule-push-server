package com.mycollege.push.infrastructure.adapters.input.controllers;

import com.mycollege.push.infrastructure.configuration.RuStoreConfiguration;
import com.mycollege.push.infrastructure.configuration.SecurityConfiguration;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PushController {

    private final String PROJECT_ID;
    private final String SERVICE_TOKEN;
    private final String SECRET;
    @Autowired
    public PushController(RuStoreConfiguration properties, SecurityConfiguration conf) {
        this.PROJECT_ID = properties.getProjectId();
        this.SERVICE_TOKEN = properties.getServiceToken();
        this.SECRET = conf.getSecret();
    }

    @PostMapping("/push")
    public String push(@org.springframework.web.bind.annotation.RequestBody String key) {
        if (key.equals(SECRET)) {
            OkHttpClient client = new OkHttpClient();

            String url = "https://vkpns.rustore.ru/v1/projects/" + PROJECT_ID + "/messages:send";

            String push_token = "hleEA9i8tVVDQiFK25nDr-LlUK8gO8jk";
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
                """, push_token, body, title);

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

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    return "Response: " + response.body().string();
                } else {
                    return "Request failed: " + response.code() + " - " + response.message();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "Unauthorized";
    }

}
