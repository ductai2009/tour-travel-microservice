package com.edo.auth_service.controller;


import com.edo.auth_service.dto.request.AuthRequest;
import com.edo.auth_service.dto.request.IntrospectRequest;
import com.edo.auth_service.dto.request.InvalidateTokenRequest;
import com.edo.auth_service.dto.request.ResponseData;
import com.edo.auth_service.dto.response.AuthResponse;
import com.edo.auth_service.dto.response.IntrospectResponse;
import com.edo.auth_service.service.AuthService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthRequest authRequest;

    @Autowired
    AuthService authService;

    @PostMapping("/log-in")
    public ResponseData<AuthResponse> isAuth(@RequestBody AuthRequest request) throws KeyLengthException {
        String resultToken = authService.authentication(request);

        return ResponseData.<AuthResponse>builder()
                .result(AuthResponse.builder()
                        .token(resultToken)
                        .build())
                .build();
    }

    @GetMapping("/logout")
    public ResponseData<String> logout() throws JOSEException, ParseException {
        authService.logout();

        return ResponseData.<String>builder()
                .result("Logout successfully")
                .build();
    }

    @PostMapping("/introspect-token")
    public ResponseData<IntrospectResponse> IntrospectToken(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse resultToken = authService.IntrospectToken(request);
        return ResponseData.<IntrospectResponse>builder()
                .result(IntrospectResponse.builder()
                        .valid(resultToken.isValid())
                        .build())
                .build();
    }

}
