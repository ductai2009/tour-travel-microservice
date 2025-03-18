package com.edo.microservices.api_gateway.config;

import com.edo.microservices.api_gateway.service.AuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtUtil {
    @Autowired
    AuthServiceClient authServiceClient;

    public boolean validateToken(final String token) {
       return authServiceClient.introspectTokenSync(token);
    }



}
