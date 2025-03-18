package com.edo.microservices.api_gateway.config;



import com.edo.microservices.api_gateway.exception.AppException;
import com.edo.microservices.api_gateway.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new AppException(ErrorCode.INVALID_TOKEN);
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    if (!jwtUtil.validateToken(authHeader))
                        throw new AppException(ErrorCode.INVALID_TOKEN);
                } catch (Exception e) {
                    log.error(e.toString());
                    throw new AppException(ErrorCode.UNAUTHORIZED);
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
