package com.edo.microservices.api_gateway.service;


import com.edo.microservices.api_gateway.dto.response.TokenResponse;
import com.edo.microservices.api_gateway.exception.AppException;
import com.edo.microservices.api_gateway.exception.ErrorCode;
import kong.unirest.JsonNode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@Slf4j
@Service
public class AuthServiceClient {
    @Autowired
    WebClient webClient;

    @Value("${server-auth.host}")
    private String HOST_AUTH_SERVICE;

    public boolean introspectTokenSync(String token) {
        try {
            HttpResponse<JsonNode> response = Unirest.post(HOST_AUTH_SERVICE+"/auth-service/auth/introspect-token")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body("{ \"token\": \"" + token + "\" }")
                    .asJson();


            if (response.getStatus() == 200) {
                JsonNode responseBody = response.getBody();

                int code = responseBody.getObject().getInt("code");

                if(code != 1000)
                    return false;

                return responseBody.getObject()
                        .getJSONObject("result")
                        .getBoolean("valid");
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);

        }
        return false;
    }


}

