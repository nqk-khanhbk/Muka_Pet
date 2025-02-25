package com.muka.petcare.config;

import com.muka.petcare.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    //thông tin jwt
    private final JwtProperties jwtProperties;
    private final JwtUtils jwtUtils;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            boolean checkToken = jwtUtils.checkTokenValidFull(token, true);
            if(!checkToken) throw new JwtException("Token is invalid");
        }
        catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(jwtProperties.getAccessTokenSecret().getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
