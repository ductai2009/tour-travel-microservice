package com.edo.auth_service.service;

import com.edo.auth_service.dto.request.AuthRequest;
import com.edo.auth_service.dto.request.IntrospectRequest;
import com.edo.auth_service.dto.request.InvalidateTokenRequest;
import com.edo.auth_service.dto.response.AuthResponse;
import com.edo.auth_service.dto.response.IntrospectResponse;
import com.edo.auth_service.entity.InvalidateToken;
import com.edo.auth_service.entity.Role;
import com.edo.auth_service.entity.User;
import com.edo.auth_service.exception.AppException;
import com.edo.auth_service.exception.ErrorCode;
import com.edo.auth_service.repository.InvalidateTokenRepository;
import com.edo.auth_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    InvalidateTokenRepository invalidateTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;


    public String authentication(AuthRequest request) throws KeyLengthException {

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);


        return generateToken(user);
    }

    public String getCurrentToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth))
            throw new AppException(ErrorCode.UNAUTHENTICATED);


        return jwtAuth.getToken().getTokenValue();

    }

    public void logout() throws ParseException, JOSEException {
    try {

        SignedJWT signedJWT = verifyToken(getCurrentToken());

        String jit = signedJWT.getJWTClaimsSet().getJWTID();

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .id(jit)
                .expirationTime(expiryTime)
                .build();
        invalidateTokenRepository.save(invalidateToken);
    } catch (Exception e) {
        log.info("token not verify");
    }

    }

    public IntrospectResponse IntrospectToken(IntrospectRequest request) {
        try {

            String token = request.getToken();

            if (token == null || !token.contains(".")) {
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }

            boolean isVerify = true;
            try{
                SignedJWT signedJWT = verifyToken(token);
            }catch (Exception e){
                isVerify = false;
            }


            return IntrospectResponse.builder()
                    .valid(isVerify)
                    .build();

        } catch (Exception e) {
            log.error("JWT verification failed: {}" , e.getMessage());
            return IntrospectResponse.builder().valid(false).build();
        }
    }
    public SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if(!(signedJWT.verify(verifier) && new Date().before(expiryTime)))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if(invalidateTokenRepository.existsById(jit))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;

    }


    private String generateToken(User user) throws KeyLengthException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("edo.com")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(Instant.now().plus(12, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private String buildScope(User user){

        StringJoiner stringJoiner = new StringJoiner(" ");

        if(!CollectionUtils.isEmpty(user.getRole())){
            user.getRole().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }

        return stringJoiner.toString();
    }
}
